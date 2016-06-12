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
 * Команда для обновления даты и времени получения пользователем автомобиля
 */
public class UpdateRealDateFromCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(UpdateRealDateToCommand.class.getName());
    private static final String EXECUTE_STARTS = "UpdateRealDateToCommand : execute";
    private static final String ORDER_ID_PARAM = "selectedOrderId";
    private static final String REAL_DATE_FROM = "real-date-from";
    private static final String REAL_TIME_FROM = "real-time-from";
    private static final String INVALID_DATE_FROM = "invalidDateFrom";
    private static final String DATE_FROM_UPDATED_PARAM = "dateFromUpdated";
    private static final String SELECTED_ORDER_PARAM = "selectedOrder";
    private static final String PROCESS_REQUEST_PARAM = "processRequest";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int orderId = Integer.parseInt(request.getParameter(ORDER_ID_PARAM));
        String realDateFrom = request.getParameter(REAL_DATE_FROM) + " " + request.getParameter(REAL_TIME_FROM);
        OrderService service = OrderService.getInstance();
        Validator validator = Validator.getInstance();
        try {
            if (validator.validateRealDateFrom(realDateFrom)) {
                service.updateRealTimeFrom(orderId, realDateFrom);
                request.getSession().setAttribute(DATE_FROM_UPDATED_PARAM, true);
                request.setAttribute(PROCESS_REQUEST_PARAM, "redirect");
                return PageName.ADMIN_SUCCESS;
            } else {
                request.setAttribute(ORDER_ID_PARAM, orderId);
                Order order = service.takeAdminOrderByOrderId(orderId);
                request.setAttribute(SELECTED_ORDER_PARAM, order);
                request.setAttribute(PROCESS_REQUEST_PARAM, null);
                request.setAttribute(INVALID_DATE_FROM, true);
                return PageName.ADMIN_ORDER;
            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
