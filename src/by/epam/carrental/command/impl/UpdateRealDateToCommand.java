package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.Order;
import by.epam.carrental.service.OrderService;
import by.epam.carrental.service.Validator;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Команда для вставки времени и даты, когда поьзователь вернул автомобиль
 */
public class UpdateRealDateToCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(UpdateRealDateFromCommand.class.getName());
    private static final String EXECUTE_STARTS = "UpdateRealDateFromCommand : execute";
    private static final String ORDER_ID_PARAM = "selectedOrderId";
    private static final String SELECTED_ORDER_PARAM = "selectedOrder";
    private static final String REAL_DATE_TO = "real-date-to";
    private static final String REAL_TIME_TO = "real-time-to";
    private static final String INVALID_DATE_TO = "invalidDateTo";
    private static final String DATE_TO_UPDATED_PARAM = "dateToUpdated";
    private static final String PROCESS_REQUEST_PARAM = "processRequest";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int orderId = (Integer) request.getSession().getAttribute("orderId");
        String realDateTo = request.getParameter(REAL_DATE_TO) + " " + request.getParameter(REAL_TIME_TO);
        OrderService service = OrderService.getInstance();
        Validator validator = Validator.getInstance();
        try {
            Order order = service.takeAdminOrderByOrderId(orderId);
            String realDateFrom = order.getRealDateFrom();
            if (validator.validateDate(realDateFrom, realDateTo)) {
                service.updateRealTimeTo(orderId, realDateTo);
                request.setAttribute(PROCESS_REQUEST_PARAM, "redirect");
                request.getSession().setAttribute(DATE_TO_UPDATED_PARAM, true);
                request.getSession().setAttribute("fromDate", false);
                return PageName.ADMIN_SUCCESS;
            } else {
                request.setAttribute(ORDER_ID_PARAM, orderId);
                order = service.takeAdminOrderByOrderId(orderId);
                request.setAttribute(SELECTED_ORDER_PARAM, order);
                request.setAttribute(PROCESS_REQUEST_PARAM, "forward");
                request.setAttribute(INVALID_DATE_TO, true);
                return PageName.DATE;
            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
