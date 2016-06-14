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
 * Команда для получения пользователем одного своего заказа
 */
public class ViewOrderUserCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(ViewOrderUserCommand.class.getName());
    private static final String EXECUTE_STARTS = "ViewOrderUserCommand : execute";
    private static final String SELECTED_ORDER_ID_PARAM = "selectedOrderId";
    private static final String SELECTED_ORDER_PARAM = "selectedOrder";
    private static final String ORDER_ID_PARAM = "orderId";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int orderId = Integer.parseInt(request.getParameter(SELECTED_ORDER_ID_PARAM));
        OrderService service = OrderService.getInstance();
        request.setAttribute(SELECTED_ORDER_ID_PARAM, orderId);
        request.getSession().setAttribute(SELECTED_ORDER_ID_PARAM, orderId);
        Order order = null;
        try {
            order = service.findOrderByOrderId(orderId);
            request.getSession().setAttribute(SELECTED_ORDER_PARAM, order);
            request.setAttribute("processRequest", "forward");
            return PageName.USER_ORDER;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
