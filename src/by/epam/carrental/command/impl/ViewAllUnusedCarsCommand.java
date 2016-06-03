package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.Car;
import by.epam.carrental.service.Validator;
import by.epam.carrental.service.CarService;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Команда для получения всех неиспользуемых автомобилей
 */
public class ViewAllUnusedCarsCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(UpdateRealDateFromCommand.class.getName());

    private static final String EXECUTE_STARTS_MSG = "ViewAllUnusedCarsCommand : execute";

    private static final String CAR_TYPE_PARAM = "carType";
    private static final String SUPPOSED_DATE_FROM_PARAM = "supposedDateFrom";
    private static final String SUPPOSED_TIME_FROM_PARAM = "supposedTimeFrom";
    private static final String SUPPOSED_DATE_TO_PARAM = "supposedDateTo";
    private static final String SUPPOSED_TIME_TO_PARAM = "supposedTimeTo";
    private static final String INVALID_DATE_PARAM = "invalidDate";
    private static final String NO_CARS_PARAM = "noCars";
    private static final String AMOUNT_PAGES_PARAM = "amountPages";
    private static final String ALL_CARS_PARAM = "allCars";

    private static final int AMOUNT_CARS_ON_PAGE = 9;

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS_MSG);
        int amountPages = 0;
        int pageNumber = 1;

        String carType = (String) request.getSession().getAttribute(CAR_TYPE_PARAM);
        String supposedDateFrom = request.getParameter(SUPPOSED_DATE_FROM_PARAM) + " " + request.getParameter(SUPPOSED_TIME_FROM_PARAM);
        String supposedDateTo = request.getParameter(SUPPOSED_DATE_TO_PARAM) + " " + request.getParameter(SUPPOSED_TIME_TO_PARAM);
        request.getSession().setAttribute(SUPPOSED_DATE_FROM_PARAM, request.getParameter(SUPPOSED_DATE_FROM_PARAM));
        request.getSession().setAttribute(SUPPOSED_TIME_FROM_PARAM, request.getParameter(SUPPOSED_TIME_FROM_PARAM));
        request.getSession().setAttribute(SUPPOSED_DATE_TO_PARAM, request.getParameter(SUPPOSED_DATE_TO_PARAM));
        request.getSession().setAttribute(SUPPOSED_TIME_TO_PARAM, request.getParameter(SUPPOSED_TIME_TO_PARAM));
        Validator validator = Validator.getInstance();
        List<Car> cars = null;
        CarService service = CarService.getInstance();
        if (!validator.validateDate(supposedDateFrom, supposedDateTo)) {
            request.setAttribute(INVALID_DATE_PARAM, true);
            return PageName.ALL_CARS;
        }
        try {
            cars = service.takeUnusedCars(supposedDateFrom, supposedDateTo, pageNumber, AMOUNT_CARS_ON_PAGE);
            if (cars.size() == 0) {
                request.setAttribute(NO_CARS_PARAM, true);
            }
            amountPages = service.countPageAmountAllCars(AMOUNT_CARS_ON_PAGE);
            request.setAttribute(AMOUNT_PAGES_PARAM, amountPages);
            request.setAttribute(ALL_CARS_PARAM, cars);
            return PageName.ALL_CARS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
