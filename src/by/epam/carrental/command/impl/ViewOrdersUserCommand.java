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

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("ViewOrdersUserCommand : execute");
        User user = (User) request.getSession(true).getAttribute("user");
        OrderService service = OrderService.getInstance();
        List<Order> orders = null;
        try {
            orders = service.findOrdersByUserId(user.getId());
            request.getSession().setAttribute("orders", orders);
            return PageName.USER_ORDERS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
