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
import java.util.List;

/**
 * Команда для перехода на страницу личного кабинета админа
 */
public class ToPrivateOfficeAdminCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ToPrivateOfficeAdminCommand.class.getName());

    private static final String EXECUTE_STARTS_MSG = "ToPrivateOfficeAdminCommand : execute : starts";
    private static final String EXECUTE_ENDS_MSG = "ToPrivateOfficeAdminCommand : execute : ends";

    private static final String PAGE_NUMBER_PARAM = "pageNumber";
    private static final String NO_ORDERS_PARAM = "noOrders";
    private static final String ORDERS_PARAM = "orders";
    private static final String PROCESS_REQUEST_PARAM = "processRequest";

    private static final int AMOUNT_ORDERS_ON_PAGE = 2;
    private static final int DEFAULT_PAGE_NUMBER = 1;

    private static final String FORWARD_VALUE = "forward";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS_MSG);
        int pageNumber = DEFAULT_PAGE_NUMBER;
        List<Order> orders = null;
        OrderService service = OrderService.getInstance();
        try {
            orders = service.takeAllOrders(pageNumber, AMOUNT_ORDERS_ON_PAGE);
            if (orders.size() == 0) {
                request.setAttribute(NO_ORDERS_PARAM, true);
            }
            request.setAttribute(PAGE_NUMBER_PARAM, pageNumber);
            request.getSession(true).setAttribute(ORDERS_PARAM, orders);
            request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
            LOG.debug(EXECUTE_ENDS_MSG);
            return PageName.PRIV_OFF_ADMIN;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
