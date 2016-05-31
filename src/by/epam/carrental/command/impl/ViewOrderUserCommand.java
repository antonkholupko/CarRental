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
 * Команда для получения пользователем одного своего заказа
 */
public class ViewOrderUserCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(ViewOrderUserCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("ViewOrderUserCommand : execute");
        int orderId = Integer.parseInt(request.getParameter("selectedOrderId"));
        OrderService service = OrderService.getInstance();
        request.setAttribute("selectedOrderId", orderId);
        request.getSession().setAttribute("selectedOrderId", orderId);
        Order order = null;
        try {
            order = service.findOrderByOrderId(orderId);
            request.getSession().setAttribute("selectedOrder", order);
            return PageName.USER_ORDER;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
