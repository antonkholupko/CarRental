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
 * Команда получает список заказов для пользователя
 */
public class ViewOrdersUserCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ViewOrdersUserCommand.class.getName());

    private static final String EXECUTE_STARTS_MSG = "ViewOrdersUserCommand : execute";

    private static final String USER_PARAM = "user";
    private static final String NO_ORDERS_PARAM = "noOrders";
    private static final String ORDERS_PARAM = "orders";


    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS_MSG);
        User user = (User) request.getSession(true).getAttribute(USER_PARAM);
        OrderService service = OrderService.getInstance();
        List<Order> orders = null;
        try {
            orders = service.findOrdersByUserId(user.getId());
            if(orders.size() == 0) {
                request.setAttribute(NO_ORDERS_PARAM, true);
            }
            request.getSession().setAttribute(ORDERS_PARAM, orders);
            return PageName.USER_ORDERS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
