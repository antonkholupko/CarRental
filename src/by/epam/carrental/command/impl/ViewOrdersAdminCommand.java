package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.Order;
import by.epam.carrental.service.OrderService;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Команда для получения админом списка всех заказов
 */
public class ViewOrdersAdminCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ViewOrdersAdminCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("ViewOrdersAdminCommand : execute");
        OrderService service = OrderService.getInstance();
        List<Order> orders = null;
        try {
            orders = service.takeAllOrders();
            request.getSession(true).setAttribute("orders", orders);
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
        return PageName.ADMIN_ORDERS;
    }
}
