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
 * Команда для вставки времени и даты, когда поьзователь вернул автомобиль
 */
public class UpdateRealDateToCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(UpdateRealDateFromCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("UpdateRealDateFromCommand : execute");
        int orderId = (Integer) request.getSession(true).getAttribute("orderId");
        Order order = (Order) request.getSession().getAttribute("selectedOrder");
        String realDateFrom = order.getRealDateFrom();
        String realDateTo = request.getParameter("real-date-to") + " " + request.getParameter("real-time-to");
        OrderService service = OrderService.getInstance();
        Validator validator = Validator.getInstance();
        order = null;
        List<Order> orders = null;
        try {
            if (validator.validateDate(realDateFrom, realDateTo)) {
                service.updateRealTimeTo(orderId,realDateTo);
                orders = service.takeAllOrders();
                request.getSession().setAttribute("orders", orders);
                return PageName.ADMIN_ORDERS;
            } else {
                request.setAttribute("invalidDateTo", true);
                return PageName.ADMIN_ORDER;
            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
