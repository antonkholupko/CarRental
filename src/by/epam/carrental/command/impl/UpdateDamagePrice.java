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

/**
 * Команда для установления/изменения стоимости ущерба, нанесенног опользователем
 */
public class UpdateDamagePrice implements Command {

    private static final Logger LOG = LogManager.getLogger(UpdateDamagePrice.class.getName());

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("UpdateDamagePrice : execute");
        int orderId = (Integer) request.getSession(true).getAttribute("orderId");
        Double damagePrice = Double.parseDouble(request.getParameter("damage-price"));
        Validator validator = Validator.getInstance();
        OrderService service = OrderService.getInstance();
        Order order = new Order();
        try {
            if (validator.validateDamagePrice(damagePrice)) {
                service.updateDamagePriceByOrderId(orderId, damagePrice);
                service.updateStatusById("ожидаетКомп", orderId);
                order = service.takeAdminOrderByOrderId(orderId);
                request.getSession().setAttribute("selectedOrder", order);
                request.setAttribute("invalidDamagePrice", false);
                return PageName.ADMIN_ORDER;
            } else {
                order = service.takeAdminOrderByOrderId(orderId);
                request.setAttribute("selectedOrder", order);
                request.setAttribute("invalidDamagePrice", true);
                return PageName.ADMIN_ORDER;

            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
