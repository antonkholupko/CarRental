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
 * Команда для оплаты заказа пользователем
 */
public class PayCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(PayCommand.class.getName());
    private static final String STATUS = "оплачен";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("PayCommand : execute");
        User user = null;
        int orderId = (Integer) request.getSession().getAttribute("selectedOrderId");
        user = (User) request.getSession().getAttribute("user");
        int userId = user.getId();
        OrderService service = OrderService.getInstance();
        Order order = null;
        List<Order> orders = null;
        try {
            service.updateStatusById(STATUS, orderId);
            orders = service.findOrdersByUserId(userId);
            request.getSession().setAttribute("orders", orders);
            return PageName.USER_ORDERS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
