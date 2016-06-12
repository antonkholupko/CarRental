package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.service.OrderService;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Команда для отмены заказа пользователем
 * <p>
 *     Класс CancelOrderCommand представляет команду для отмены закза пользователем.
 * </p>
 */
public class CancelOrderCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(CancelOrderCommand.class.getName());

    private static final String EXECUTE_MSG = "CancelOrderCommand : execute : starts";

    private static final String STATUS = "canceled";
    private static final int AMOUNT_ORDERS_ON_PAGE = 4;

    private static final String SELECTED_ORDER_ID_PARAM = "selectedOrderId";
    private static final String ALL_ORDERS_PARAM = "orders";
    private static final String SUCCESSFUL_CANCELED = "successfulCanceled";
    private static final String PAGE_NUMBER_PARAM = "pageNumber";
    private static final String AMOUNT_PAGES_PARAM = "amountPages";
    private static final String USER_PARAM = "user";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_MSG);
        int orderId = (Integer) request.getSession().getAttribute(SELECTED_ORDER_ID_PARAM);
        OrderService service = OrderService.getInstance();
        try {
            service.updateStatusById(STATUS, orderId);
            request.getSession().setAttribute(SUCCESSFUL_CANCELED, true);
            request.setAttribute("processRequest", "redirect");
            return PageName.USER_SUCCESS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
