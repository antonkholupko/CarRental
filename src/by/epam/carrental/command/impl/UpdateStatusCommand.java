package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.Order;
import by.epam.carrental.service.Validator;
import by.epam.carrental.service.OrderService;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Команда для изменения статуса заказа
 */
public class UpdateStatusCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(UpdateStatusCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("UpdateStatusCommand : execute");
        int orderId = (Integer) request.getSession(true).getAttribute("orderId");
        String status = request.getParameter("statusOrder");
        String orderInfo = request.getParameter("order-info");
        OrderService service = OrderService.getInstance();
        Order order = null;
        List<Order> orders = null;
        Validator validator = Validator.getInstance();
        try {
            if (validator.validateOrderInfo(orderInfo)) {
                service.updateStatusById(status, orderId, orderInfo);
                orders = service.takeAllOrders();
                request.getSession().setAttribute("orders", orders);
                request.setAttribute("invalidInfo", false);
                return PageName.ADMIN_ORDERS;
            } else {
                request.setAttribute("invalidInfo", true);
                return PageName.ADMIN_ORDER;
            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
