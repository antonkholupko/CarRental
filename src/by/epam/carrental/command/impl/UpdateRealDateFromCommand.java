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

    private static final String EXECUTE_STARTS = "UpdateRealDateToCommand : execute : starts";
    private static final String EXECUTE_ENDS = "UpdateRealDateToCommand : execute : ends";

    private static final String ORDER_ID_PARAM = "selectedOrderId";
    private static final String ID_PARAM = "orderId";
    private static final String REAL_DATE_FROM = "real-date-from";
    private static final String REAL_TIME_FROM = "real-time-from";
    private static final String INVALID_DATE_FROM = "invalidDateFrom";
    private static final String DATE_FROM_UPDATED_PARAM = "dateFromUpdated";
    private static final String SELECTED_ORDER_PARAM = "selectedOrder";
    private static final String PROCESS_REQUEST_PARAM = "processRequest";
    private static final String FROM_DATE_PARAM = "fromDate";

    private static final String FORWARD_VALUE = "forward";
    private static final String REDIRECT_VALUE = "redirect";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int orderId = (Integer) request.getSession().getAttribute(ID_PARAM);
        String realDateFrom = request.getParameter(REAL_DATE_FROM) + " " + request.getParameter(REAL_TIME_FROM);
        OrderService service = OrderService.getInstance();
        Validator validator = Validator.getInstance();
        try {
            if (validator.validateRealDateFrom(realDateFrom)) {
                service.updateRealTimeFrom(orderId, realDateFrom);
                request.getSession().setAttribute(DATE_FROM_UPDATED_PARAM, true);
                request.setAttribute(PROCESS_REQUEST_PARAM, REDIRECT_VALUE);
                request.getSession().setAttribute(FROM_DATE_PARAM, false);
                LOG.debug(EXECUTE_ENDS);
                return PageName.ADMIN_SUCCESS;
            } else {
                request.setAttribute(ORDER_ID_PARAM, orderId);
                Order order = service.takeAdminOrderByOrderId(orderId);
                request.setAttribute(SELECTED_ORDER_PARAM, order);
                request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
                request.setAttribute(INVALID_DATE_FROM, true);
                LOG.debug(EXECUTE_ENDS);
                return PageName.DATE;
            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
