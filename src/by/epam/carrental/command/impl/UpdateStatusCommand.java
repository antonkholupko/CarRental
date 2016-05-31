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
        Validator validator = Validator.getInstance();
        try {
            if (validator.validateOrderInfo(orderInfo) == true) {
                service.updateStatusById(status, orderId, orderInfo);
                order = service.takeAdminOrderByOrderId(orderId);
                request.getSession().setAttribute("selectedOrder", order);
                request.setAttribute("invalidInfo", false);
                request.setAttribute("successfulUpdate", true);
                return PageName.ADMIN_ORDER;
            } else {
                request.setAttribute("invalidInfo", true);
                return PageName.ADMIN_ORDER;
            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
