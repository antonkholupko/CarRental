package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.User;
import by.epam.carrental.service.OrderService;
import by.epam.carrental.service.Validator;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Команда для оформления заказа пользователем
 */
public class MakeOrderCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(MakeOrderCommand.class.getName());

    private static final String EXECUTE_STARTS_MSG = "MakeOrderCommand : execute : starts";
    private static final String EXECUTE_ENDS_MSG = "MakeOrderCommand : execute : ends";

    private static final String USER_PARAM = "user";
    private static final String SELECTED_CAR_PARAM_ID = "selectedCarId";
    private static final String SUPPOSED_DATE_FROM_PARAM = "supposedDateFrom";
    private static final String SUPPOSED_DATE_TO_PARAM = "supposedDateTo";
    private static final String SUPPOSED_TIME_FROM_PARAM = "supposedTimeFrom";
    private static final String SUPPOSED_TIME_TO_PARAM = "supposedTimeTo";
    private static final String SPACE = " ";
    private static final String SHIPPING_PLACE_PARAM = "shippingPlace";
    private static final String RETURN_PLACE_PARAM = "returnPlace";
    private static final String INV_DATE_PARAM = "invalidDate";
    private static final String INV_PLACES_PARAM = "invalidPlaces";
    private static final String ADD_ORDER_FAILED_PARAM = "addOrderFailed";
    private static final String ORDER_SUCCESSFUL_MADE = "orderSuccessfulMade";
    private static final String PROCESS_REQUEST_PARAM = "processRequest";

    private static final String FORWARD_VALUE = "forward";
    private static final String REDIRECT_VALUE = "redirect";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS_MSG);
        User user = (User) request.getSession(true).getAttribute(USER_PARAM);
        int userId = user.getId();
        int carId = (Integer) request.getSession(true).getAttribute(SELECTED_CAR_PARAM_ID);
        String supposedDateFrom = request.getParameter(SUPPOSED_DATE_FROM_PARAM) + SPACE + request.getParameter(SUPPOSED_TIME_FROM_PARAM);
        String supposedDateTo = request.getParameter(SUPPOSED_DATE_TO_PARAM) + SPACE + request.getParameter(SUPPOSED_TIME_TO_PARAM);
        String shippingPlace = request.getParameter(SHIPPING_PLACE_PARAM);
        String returnPlace = request.getParameter(RETURN_PLACE_PARAM);
        OrderService service = OrderService.getInstance();
        Validator validator = Validator.getInstance();
        try {
            if (!validator.validateDate(supposedDateFrom, supposedDateTo)) {
                request.setAttribute(INV_DATE_PARAM, true);
                request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
                LOG.debug(EXECUTE_ENDS_MSG);
                return PageName.MAKE_ORDER;
            }

            if (!validator.validatePlace(shippingPlace) || !validator.validatePlace(returnPlace)) {
                request.setAttribute(INV_PLACES_PARAM, true);
                request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
                LOG.debug(EXECUTE_ENDS_MSG);
                return PageName.MAKE_ORDER;
            }
            boolean add = service.addOrder(userId, carId, supposedDateFrom, supposedDateTo, shippingPlace, returnPlace);
            if (!add) {
                request.setAttribute(ADD_ORDER_FAILED_PARAM, true);
                request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
                LOG.debug(EXECUTE_ENDS_MSG);
                return PageName.MAKE_ORDER;
            }
            request.getSession().setAttribute(ORDER_SUCCESSFUL_MADE, true);
            request.setAttribute(PROCESS_REQUEST_PARAM, REDIRECT_VALUE);
            LOG.debug(EXECUTE_ENDS_MSG);
            return PageName.USER_SUCCESS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
