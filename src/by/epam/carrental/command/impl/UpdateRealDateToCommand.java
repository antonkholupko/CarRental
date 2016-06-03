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
    private static final String EXECUTE_STARTS = "UpdateRealDateFromCommand : execute";
    private static final String ORDER_ID_PARAM = "orderId";
    private static final String SELECTED_ORDER_PARAM = "selectedOrder";
    private static final String REAL_DATE_TO = "real-date-to";
    private static final String REAL_TIME_TO = "real-time-to";
    private static final String ORDERS_PARAM = "orders";
    private static final String INVALID_DATE_TO = "invalidDateTo";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int orderId = (Integer) request.getSession(true).getAttribute(ORDER_ID_PARAM);
        Order order = (Order) request.getSession().getAttribute(SELECTED_ORDER_PARAM);
        String realDateFrom = order.getRealDateFrom();
        String realDateTo = request.getParameter(REAL_DATE_TO) + " " + request.getParameter(REAL_TIME_TO);
        OrderService service = OrderService.getInstance();
        Validator validator = Validator.getInstance();
        order = null;
        List<Order> orders = null;
        try {
            if (validator.validateDate(realDateFrom, realDateTo)) {
                service.updateRealTimeTo(orderId,realDateTo);
                orders = service.takeAllOrders();
                request.getSession().setAttribute(ORDERS_PARAM, orders);
                return PageName.ADMIN_ORDERS;
            } else {
                request.setAttribute(INVALID_DATE_TO, true);
                return PageName.ADMIN_ORDER;
            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
