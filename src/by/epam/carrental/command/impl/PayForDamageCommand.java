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
import java.util.List;

/**
 * Команда для оплаты заказа поьзователем
 */
public class PayForDamageCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(PayCommand.class.getName());
    private static final String EXECUTE_STARTS = "PayForDamageCommand : execute";
    private static final String STATUS_VALUE = "closed";
    private static final String SELECTED_ORDER_ID_PARAM = "selectedOrderId";
    private static final String USER_PARAM = "user";
    private static final String ORDERS_PARAM = "orders";
    private static final String SUCCESSFUL_PAYMENT_PARAM = "successfulPayment";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        User user = null;
        user = (User) request.getSession().getAttribute(USER_PARAM);
        int userId = user.getId();
        int orderId = (Integer) request.getSession().getAttribute(SELECTED_ORDER_ID_PARAM);
        OrderService service = OrderService.getInstance();
        Order order = null;
        List<Order> orders = null;
        try {
            service.updateStatusById(STATUS_VALUE, orderId);
            orders = service.findOrdersByUserId(userId);
            request.getSession().setAttribute(ORDERS_PARAM, orders);
            request.setAttribute(SUCCESSFUL_PAYMENT_PARAM, true);
            return PageName.USER_ORDERS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
