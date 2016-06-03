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
 * Команда для отмены заказа пользователем
 * <p>
 *     Класс CancelOrderCommand представляет команду для отмены закза пользователем.
 * </p>
 */
public class CancelOrderCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(CancelOrderCommand.class.getName());

    private static final String EXECUTE_MSG = "CancelOrderCommand : execute : starts";

    private static final String STATUS = "отменен";

    private static final String SELECTED_ORDER_ID_PARAM = "selectedOrderId";
    private static final String SELECTED_ORDER_PARAM = "selectedOrder";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_MSG);
        int orderId = (Integer) request.getSession().getAttribute(SELECTED_ORDER_ID_PARAM);
        OrderService service = OrderService.getInstance();
        Order order = null;
        try {
            service.updateStatusById(STATUS, orderId);
            order = service.findOrderByOrderId(orderId);
            request.getSession().setAttribute(SELECTED_ORDER_PARAM, order);
            return PageName.USER_ORDER;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
