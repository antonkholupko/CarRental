package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Команда получает список автомобилей определенного типа
 */
public class ViewTypeByDateCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ViewTypeByDateCommand.class.getName());

    private static final String CAR_TYPE_PARAM = "carType";
    private static final String SUPPOSED_DATE_FROM_PARAM = "supposedDateFrom";
    private static final String SUPPOSED_TIME_FROM_PARAM = "supposedTimeFrom";
    private static final String SUPPOSED_DATE_TO_PARAM = "supposedDateTo";
    private static final String SUPPOSED_TIME_TO_PARAM = "supposedTimeTo";
    private static final String INVALID_DATE_PARAM = "invalidDate";
    private static final String ALL_CARS_PARAM = "allCars";
    private static final String USER_PARAM = "user";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("ViewTypeCommand : execute");
        User user = (User) request.getSession(true).getAttribute(USER_PARAM);
        String type = request.getParameter(CAR_TYPE_PARAM);
        String supposedDateFrom = null;
        String supposedDateTo = null;
        /*request.getSession().setAttribute(CAR_TYPE_PARAM, type);
        if (request.getSession().getAttribute(SUPPOSED_DATE_FROM_PARAM) != null && request.getSession().getAttribute(SUPPOSED_TIME_FROM_PARAM) != null
                && request.getSession().getAttribute(SUPPOSED_DATE_TO_PARAM) != null && request.getSession().getAttribute(SUPPOSED_TIME_TO_PARAM) != null) {
            supposedDateFrom = request.getSession(true).getAttribute(SUPPOSED_DATE_FROM_PARAM) + " " + request.getSession(true).getAttribute(SUPPOSED_TIME_FROM_PARAM);
            supposedDateTo = request.getSession(true).getAttribute(SUPPOSED_DATE_TO_PARAM) + " " + request.getSession(true).getAttribute(SUPPOSED_TIME_TO_PARAM);
        }
        CarService service = CarService.getInstance();
        Validator validator = Validator.getInstance();
        List<Car> cars = null;
        try {
            if (supposedDateFrom != null && supposedDateTo != null && !validator.validateDate(supposedDateFrom, supposedDateTo)) {
                request.setAttribute(INVALID_DATE_PARAM, true);
                request.setAttribute("processRequest", "forward");
                return PageName.ALL_CARS;
            } else {
                request.getSession().setAttribute(ALL_CARS_PARAM, cars);
                throw new ServiceException("");

            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }*/
        return null;
    }
}
