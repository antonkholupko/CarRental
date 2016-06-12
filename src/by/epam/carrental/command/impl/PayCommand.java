package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.User;
import by.epam.carrental.service.OrderService;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Команда для оплаты заказа пользователем
 */
public class PayCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(PayCommand.class.getName());
    private static final String EXECUTE_STARTS = "PayCommand : execute";
    private static final String SELECTED_ORDER_ID_PARAM = "selectedOrderId";
    private static final String USER_PARAM = "user";
    private static final String STATUS_VALUE = "payed";
    private static final String ORDERS_PARAM = "orders";
    private static final String PAGE_NUMBER_PARAM = "pageNumber";
    private static final String AMOUNT_PAGES_PARAM = "amountPages";
    private static final String SUCCESSFUL_PAYMENT = "successfulPayment";

    private static final int AMOUNT_ORDERS_ON_PAGE = 4;

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        User user = null;
        int orderId = (Integer) request.getSession().getAttribute(SELECTED_ORDER_ID_PARAM);
        OrderService service = OrderService.getInstance();
        try {
            service.updateStatusById(STATUS_VALUE, orderId);
            request.getSession().setAttribute(SUCCESSFUL_PAYMENT, true);
            request.setAttribute("processRequest", "redirect");
            return PageName.USER_SUCCESS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
