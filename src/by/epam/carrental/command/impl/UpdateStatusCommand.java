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
 * Команда для изменения статуса заказа
 */
public class UpdateStatusCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(UpdateStatusCommand.class.getName());
    private static final String EXECUTE_STARTS = "UpdateStatusCommand : execute";
    private static final String ORDER_ID_PARAM = "orderId";
    private static final String ORDER_STATUS_PARAM = "statusOrder";
    private static final String ORDER_INFO_PARAM = "order-info";
    private static final String ORDERS_PARAM = "orders";
    private static final String INVALID_INFO = "invalidInfo";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int orderId = (Integer) request.getSession(true).getAttribute(ORDER_ID_PARAM);
        String status = request.getParameter(ORDER_STATUS_PARAM);
        String orderInfo = request.getParameter(ORDER_INFO_PARAM);
        OrderService service = OrderService.getInstance();
        Order order = null;
        List<Order> orders = null;
        Validator validator = Validator.getInstance();
        try {
            if (validator.validateOrderInfo(orderInfo)) {
                service.updateStatusById(status, orderId, orderInfo);
                orders = service.takeAllOrders();
                request.getSession().setAttribute(ORDERS_PARAM, orders);
                request.setAttribute(INVALID_INFO, false);
                return PageName.ADMIN_ORDERS;
            } else {
                request.setAttribute(INVALID_INFO, true);
                return PageName.ADMIN_ORDER;
            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
