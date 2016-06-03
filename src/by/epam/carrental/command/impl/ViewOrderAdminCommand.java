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

/**
 * Команда для предоставления админу информации о заказе
 */
public class ViewOrderAdminCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ViewOrderAdminCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("ViewOrderAdminCommand : execute");
        int orderId = Integer.parseInt(request.getParameter("selectedOrderId"));
        request.getSession().setAttribute("orderId", orderId);
        OrderService service = OrderService.getInstance();
        Order order = null;
        try {
            order = service.takeAdminOrderByOrderId(orderId);
            request.getSession().setAttribute("selectedOrder", order);
            return PageName.ADMIN_ORDER;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
