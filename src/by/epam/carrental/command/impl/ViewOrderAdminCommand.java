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

/**
 * Команда для предоставления админу информации о заказе
 */
public class ViewOrderAdminCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ViewOrderAdminCommand.class.getName());
    private static final String EXECUTE_STARTS = "ViewOrderAdminCommand : execute";
    private static final String SELECTED_ORDER_ID_PARAM = "selectedOrderId";
    private static final String SELECTED_ORDER_PARAM = "selectedOrder";
    private static final String ORDER_ID_PARAM = "orderId";


    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int orderId = 0;
        if (request.getSession().getAttribute(ORDER_ID_PARAM) != null) {
            orderId = (Integer) request.getSession().getAttribute(ORDER_ID_PARAM);
        } else {
            orderId = Integer.parseInt(request.getParameter(SELECTED_ORDER_ID_PARAM));
        }
        request.getSession().setAttribute(ORDER_ID_PARAM, orderId);
        OrderService service = OrderService.getInstance();
        Order order = null;
        try {
            order = service.takeAdminOrderByOrderId(orderId);
            request.getSession().setAttribute(SELECTED_ORDER_PARAM, order);
            return PageName.ADMIN_ORDER;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
