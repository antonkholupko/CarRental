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
    private static final String EXECUTE_STARTS = "ToPrivateOfficeAdminCommand : execute";
    private static final String PAGE_NUMBER_PARAM = "pageNumber";
    private static final String AMOUNT_PAGES_PARAM = "amountPages";
    private static final String NO_ORDERS_PARAM = "noOrders";
    private static final String ORDERS_PARAM = "orders";

    private static final int AMOUNT_ORDERS_ON_PAGE = 2;

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int amountPages = 0;
        int pageNumber = 1;
        List<Order> orders = null;
        OrderService service = OrderService.getInstance();
        try {
            orders = service.takeAllOrders(pageNumber, AMOUNT_ORDERS_ON_PAGE);
            if (orders.size() == 0) {
                request.setAttribute(NO_ORDERS_PARAM, true);
            }
            amountPages = service.countPageAmountAllOrders(AMOUNT_ORDERS_ON_PAGE);
            request.setAttribute(AMOUNT_PAGES_PARAM, amountPages);
            request.setAttribute(PAGE_NUMBER_PARAM, pageNumber);
            request.getSession(true).setAttribute(ORDERS_PARAM, orders);
            return PageName.PRIV_OFF_ADMIN;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
