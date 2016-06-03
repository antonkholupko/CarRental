package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.Car;
import by.epam.carrental.entity.Order;
import by.epam.carrental.entity.User;
import by.epam.carrental.service.Validator;
import by.epam.carrental.service.OrderService;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Команда для оформления заказа пользователем
 */
public class MakeOrderCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(MakeOrderCommand.class.getName());



    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("MakeOrderCommand : execute");
        User user = (User) request.getSession(true).getAttribute("user");
        int userId = user.getId();
        int carId = ((Car) request.getSession(true).getAttribute("selectedCar")).getId();
        String supposedDateFrom = request.getParameter("supposedDateFrom") + " " + request.getParameter("supposedTimeFrom");
        String supposedDateTo = request.getParameter("supposedDateTo") + " " + request.getParameter("supposedTimeTo");
        String shippingPlace = request.getParameter("shippingPlace");
        String returnPlace = request.getParameter("returnPlace");
        OrderService service = OrderService.getInstance();
        Validator validator = Validator.getInstance();
        List<Order> orders = null;
        try {
            if (!validator.validateDate(supposedDateFrom, supposedDateTo)) {
                request.setAttribute("invalidDate", true);
                return PageName.MAKE_ORDER;
            }

            if (!validator.validatePlace(shippingPlace) && !validator.validatePlace(returnPlace)) {
                request.setAttribute("invalidPlaces", true);
                return PageName.MAKE_ORDER;
            }
            boolean add = service.addOrder(userId, carId, supposedDateFrom, supposedDateTo, shippingPlace, returnPlace);
            if (!add) {
                request.setAttribute("addOrderFailed", true);
                return PageName.MAKE_ORDER;
            }
            orders = service.findOrdersByUserId(user.getId());
            request.getSession().setAttribute("orders", orders);
            return PageName.USER_ORDERS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
