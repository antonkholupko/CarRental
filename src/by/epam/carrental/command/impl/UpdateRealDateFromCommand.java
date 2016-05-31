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
 * Команда для обновления даты и времени получения пользователем автомобиля
 */
public class UpdateRealDateFromCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(UpdateRealDateToCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("UpdateRealDateToCommand : execute");
        int orderId = (Integer) request.getSession(true).getAttribute("orderId");
        String realDateFrom = request.getParameter("real-date-from") + " " + request.getParameter("real-time-from");
        OrderService service = OrderService.getInstance();
        Validator validator = Validator.getInstance();
        Order order = null;
        List<Order> orders = null;
        try {
            if (validator.validateRealDateFrom(realDateFrom)) {
                service.updateRealTimeFrom(orderId,realDateFrom);
                orders = service.takeAllOrders();
                request.getSession().setAttribute("orders", orders);
                return PageName.ADMIN_ORDERS;
            } else {
                request.setAttribute("invalidDateFrom", true);
                return PageName.ADMIN_ORDER;
            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
