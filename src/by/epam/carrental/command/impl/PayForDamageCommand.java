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
    private static final String STATUS = "закрыт";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("PayForDamageCommand : execute");
        User user = null;
        user = (User) request.getSession().getAttribute("user");
        int userId = user.getId();
        int orderId = (Integer) request.getSession().getAttribute("selectedOrderId");
        OrderService service = OrderService.getInstance();
        Order order = null;
        List<Order> orders = null;
        try {
            service.updateStatusById(STATUS, orderId);
            orders = service.findOrdersByUserId(userId);
            request.getSession().setAttribute("orders", orders);
            request.setAttribute("successfulPayment", true);
            return PageName.USER_ORDERS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
