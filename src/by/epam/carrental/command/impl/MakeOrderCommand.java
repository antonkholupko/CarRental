package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.Order;
import by.epam.carrental.entity.User;
import by.epam.carrental.service.OrderService;
import by.epam.carrental.service.Validator;
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

    private static final String EXECUTE_STARTS = "MakeOrderCommand : execute";
    private static final String USER_PARAM = "user";
    private static final String SELECTED_CAR_PARAM_ID = "selectedCarId";
    private static final String SUPPOSED_DATE_FROM_PARAM = "supposedDateFrom";
    private static final String SUPPOSED_DATE_TO_PARAM = "supposedDateTo";
    private static final String SUPPOSED_TIME_FROM_PARAM = "supposedTimeFrom";
    private static final String SUPPOSED_TIME_TO_PARAM = "supposedTimeTo";
    private static final String SPACE = " ";
    private static final String SHIPPING_PLACE_PARAM = "shippingPlace";
    private static final String RETURN_PLASE_PARAM = "returnPlace";
    private static final String INV_DATE_PARAM = "invalidDate";
    private static final String INV_PLACES_PARAM = "invalidPlaces";
    private static final String ADD_ORDER_FAILED_PARAM = "addOrderFailed";
    private static final String ORDERS_PARAM = "orders";
    private static final String PAGE_NUMBER_PARAM = "pageNumber";
    private static final String AMOUNT_PAGES_PARAM = "amountPages";
    private static final String ORDER_SUCCESSFUL_MADE = "orderSuccessfulMade";

    private static final int AMOUNT_ORDERS_ON_PAGE = 4;

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int amountPages = 0;
        int pageNumber = 1;
        User user = (User) request.getSession(true).getAttribute(USER_PARAM);
        int userId = user.getId();
        int carId = (Integer) request.getSession(true).getAttribute(SELECTED_CAR_PARAM_ID);
        String supposedDateFrom = request.getParameter(SUPPOSED_DATE_FROM_PARAM) + SPACE + request.getParameter(SUPPOSED_TIME_FROM_PARAM);
        String supposedDateTo = request.getParameter(SUPPOSED_DATE_TO_PARAM) + " " + request.getParameter(SUPPOSED_TIME_TO_PARAM);
        String shippingPlace = request.getParameter(SHIPPING_PLACE_PARAM);
        String returnPlace = request.getParameter(RETURN_PLASE_PARAM);
        OrderService service = OrderService.getInstance();
        Validator validator = Validator.getInstance();
        List<Order> orders = null;
        try {
            if (!validator.validateDate(supposedDateFrom, supposedDateTo)) {
                request.setAttribute(INV_DATE_PARAM, true);
                return PageName.MAKE_ORDER;
            }

            if (!validator.validatePlace(shippingPlace) && !validator.validatePlace(returnPlace)) {
                request.setAttribute(INV_PLACES_PARAM, true);
                return PageName.MAKE_ORDER;
            }
            boolean add = service.addOrder(userId, carId, supposedDateFrom, supposedDateTo, shippingPlace, returnPlace);
            if (!add) {
                request.setAttribute(ADD_ORDER_FAILED_PARAM, true);
                return PageName.MAKE_ORDER;
            }
            amountPages = service.countPageAmountUserOrders(user.getId(), AMOUNT_ORDERS_ON_PAGE);
            orders = service.findOrdersByUserId(user.getId(), pageNumber, AMOUNT_ORDERS_ON_PAGE);
            request.getSession().setAttribute(ORDERS_PARAM, orders);
            request.setAttribute(AMOUNT_PAGES_PARAM, amountPages);
            request.setAttribute(PAGE_NUMBER_PARAM, pageNumber);
            request.setAttribute(ORDER_SUCCESSFUL_MADE, true);
            return PageName.USER_ORDERS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
