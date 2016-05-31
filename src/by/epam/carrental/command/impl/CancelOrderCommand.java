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
 * Команда для отмены заказа пользователем
 * <p>
 *     Класс CancelOrderCommand представляет команду для отмены закза пользователем.
 * </p>
 */
public class CancelOrderCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(CancelOrderCommand.class.getName());
    private static final String STATUS = "отменен";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("CancelOrderCommand : execute");
        int orderId = (Integer) request.getSession().getAttribute("selectedOrderId");
        OrderService service = OrderService.getInstance();
        Order order = null;
        try {
            service.updateStatusById(STATUS, orderId);
            order = service.findOrderByOrderId(orderId);
            request.getSession().setAttribute("selectedOrder", order);
            return PageName.USER_ORDER;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
