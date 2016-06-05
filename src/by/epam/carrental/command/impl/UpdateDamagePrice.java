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
 * Команда для установления/изменения стоимости ущерба, нанесенног опользователем
 */
public class UpdateDamagePrice implements Command {

    private static final Logger LOG = LogManager.getLogger(UpdateDamagePrice.class.getName());
    private static final String EXECUTE_STARTS = "UpdateDamagePrice : execute";
    private static final String ORDER_ID_PARAM = "orderId";
    private static final String DAMAGE_PRICE_PARAM = "damage-price";
    private static final String STATUS_VALUE = "expectsComp";
    private static final String ORDERS_PARAM = "orders";
    private static final String INV_PRICE_PARAM = "invalidDamagePrice";
    private static final String SELECTED_ORDER_PARAM = "selectedOrder";


    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int orderId = (Integer) request.getSession(true).getAttribute(ORDER_ID_PARAM);
        Double damagePrice = Double.parseDouble(request.getParameter(DAMAGE_PRICE_PARAM));
        Validator validator = Validator.getInstance();
        OrderService service = OrderService.getInstance();
        Order order = new Order();
        List<Order> orders = null;
        try {
            if (validator.validateDamagePrice(damagePrice)) {
                service.updateDamagePriceByOrderId(orderId, damagePrice);
                service.updateStatusById(STATUS_VALUE, orderId);
                orders = service.takeAllOrders();
                request.getSession().setAttribute(ORDERS_PARAM, orders);
                request.setAttribute(INV_PRICE_PARAM, false);

                return PageName.ADMIN_ORDERS;
            } else {
                order = service.takeAdminOrderByOrderId(orderId);
                request.setAttribute(SELECTED_ORDER_PARAM, order);
                request.setAttribute(INV_PRICE_PARAM, true);
                return PageName.ADMIN_ORDER;

            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
