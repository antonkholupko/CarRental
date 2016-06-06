package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.Order;
import by.epam.carrental.entity.User;
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
    private static final String SELECTED_ORDER_PARAM = "selectedOrder";
    private static final String SUCCESSFUL_CANCELED = "successfulCanceled";
    private static final String PAGE_NUMBER_PARAM = "pageNumber";
    private static final String AMOUNT_PAGES_PARAM = "amountPages";
    private static final String USER_PARAM = "user";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_MSG);
        int amountPages = 0;
        int pageNumber = 1;
        int orderId = (Integer) request.getSession().getAttribute(SELECTED_ORDER_ID_PARAM);
        OrderService service = OrderService.getInstance();
        Order order = null;
        User user = null;
        user = (User) request.getSession().getAttribute(USER_PARAM);
        int userId = user.getId();
        try {
            amountPages = service.countPageAmountUserOrders(userId, AMOUNT_ORDERS_ON_PAGE);
            service.updateStatusById(STATUS, orderId);
            order = service.findOrderByOrderId(orderId);
            request.getSession().setAttribute(SELECTED_ORDER_PARAM, order);
            request.setAttribute(SUCCESSFUL_CANCELED, true);
            request.setAttribute(AMOUNT_PAGES_PARAM, amountPages);
            request.setAttribute(PAGE_NUMBER_PARAM, pageNumber);
            return PageName.USER_ORDERS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
