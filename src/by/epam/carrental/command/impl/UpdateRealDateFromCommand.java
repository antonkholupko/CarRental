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
import java.util.List;

/**
 * Команда для обновления даты и времени получения пользователем автомобиля
 */
public class UpdateRealDateFromCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(UpdateRealDateToCommand.class.getName());
    private static final String EXECUTE_STARTS = "UpdateRealDateToCommand : execute";
    private static final String ORDER_ID_PARAM = "orderId";
    private static final String REAL_DATE_FROM = "real-date-from";
    private static final String REAL_TIME_FROM = "real-time-from";
    private static final String ORDERS_PARAM = "orders";
    private static final String INVALID_DATE_FROM = "invalidDateFrom";
    private static final String PAGE_NUMBER_PARAM = "pageNumber";
    private static final String AMOUNT_PAGES_PARAM = "amountPages";

    private static final int AMOUNT_ORDERS_ON_PAGE = 4;

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int amountPages = 0;
        int pageNumber = 1;
        int orderId = (Integer) request.getSession(true).getAttribute(ORDER_ID_PARAM);
        String realDateFrom = request.getParameter(REAL_DATE_FROM) + " " + request.getParameter(REAL_TIME_FROM);
        OrderService service = OrderService.getInstance();
        Validator validator = Validator.getInstance();
        Order order = null;
        List<Order> orders = null;
        try {
            if (validator.validateRealDateFrom(realDateFrom)) {
                service.updateRealTimeFrom(orderId,realDateFrom);
                orders = service.takeAllOrders(pageNumber, AMOUNT_ORDERS_ON_PAGE);
                amountPages = service.countPageAmountAllOrders(AMOUNT_ORDERS_ON_PAGE);
                request.getSession().setAttribute(ORDERS_PARAM, orders);
                request.setAttribute(AMOUNT_PAGES_PARAM, amountPages);
                request.setAttribute(PAGE_NUMBER_PARAM, pageNumber);
                return PageName.ADMIN_ORDERS;
            } else {
                request.setAttribute(INVALID_DATE_FROM, true);
                return PageName.ADMIN_ORDER;
            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
