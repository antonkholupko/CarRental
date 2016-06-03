package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.Car;
import by.epam.carrental.service.CarService;
import by.epam.carrental.service.Validator;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ViewUnusedTypeCars implements Command {

    private static final Logger LOG = LogManager.getLogger(UpdateRealDateFromCommand.class.getName());

    private static final String EXECUTE_STARTS = "ViewUnusedTypeCars : execute : starts";
    private static final String EXECUTE_ENDS = "ViewUnusedTypeCars : execute : ends";

    private static  final int AMOUNT_CARS_ON_PAGE = 9;

    private static final String PAGE_NUMBER_PARAM = "pageNumber";
    private static final String CAR_TYPE_PARAM = "carType";
    private static final String INVALID_TYPE_PARAM = "invalidType";
    private static final String SUPPOSED_DATE_FROM_PARAM = "supposedDateFrom";
    private static final String SUPPOSED_TIME_FROM_PARAM = "supposedTimeFrom";
    private static final String SUPPOSED_DATE_TO_PARAM = "supposedDateTo";
    private static final String SUPPOSED_TIME_TO_PARAM = "supposedTimeTo";
    private static final String INVALID_DATE_PARAM = "invalidDate";
    private static final String NO_CARS_PARAM = "noCars";
    private static final String AMOUNT_PAGES_PARAM = "amountPages";
    private static final String ALL_CARS_PARAM = "allCars";
    private static final String COMMAND_PARAM = "command";

    private static final String VIEW_TYPE_UNUSED_VALUE = "view-type-unused";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int amountPages = 0;
        int pageNumber = 1;
        if (request.getParameter(PAGE_NUMBER_PARAM) != null) {
            pageNumber = Integer.parseInt(request.getParameter(PAGE_NUMBER_PARAM));
        }
        List<Car> cars = null;
        Validator validator = Validator.getInstance();
        CarService service = CarService.getInstance();
        String carType = null;
        if (request.getParameter(CAR_TYPE_PARAM) != null) {
            carType = request.getParameter(CAR_TYPE_PARAM);
            request.getSession().setAttribute(CAR_TYPE_PARAM, carType);
        } else if (request.getSession().getAttribute(CAR_TYPE_PARAM) != null) {
            carType = (String) request.getSession().getAttribute(CAR_TYPE_PARAM);
        } else {
            request.setAttribute(INVALID_TYPE_PARAM, true);
        }
        String supposedDateFrom = null;
        String supposedTimeFrom = null;
        String supposedDateTo = null;
        String supposedTimeTo = null;

        if (request.getParameter(SUPPOSED_DATE_FROM_PARAM) != null) {
            supposedDateFrom = request.getParameter(SUPPOSED_DATE_FROM_PARAM);
            supposedTimeFrom = request.getParameter(SUPPOSED_TIME_FROM_PARAM);
            supposedDateTo = request.getParameter(SUPPOSED_DATE_TO_PARAM);
            supposedTimeTo = request.getParameter(SUPPOSED_TIME_TO_PARAM);

            request.getSession().setAttribute(SUPPOSED_DATE_FROM_PARAM, supposedDateFrom);
            request.getSession().setAttribute(SUPPOSED_TIME_FROM_PARAM, supposedTimeFrom);
            request.getSession().setAttribute(SUPPOSED_DATE_TO_PARAM, supposedDateTo);
            request.getSession().setAttribute(SUPPOSED_TIME_TO_PARAM, supposedTimeTo);

        } else {
            supposedDateFrom = (String) request.getSession().getAttribute(SUPPOSED_DATE_FROM_PARAM);
            supposedTimeFrom = (String) request.getSession().getAttribute(SUPPOSED_TIME_FROM_PARAM);
            supposedDateTo = (String) request.getSession().getAttribute(SUPPOSED_DATE_TO_PARAM);
            supposedTimeTo = (String) request.getSession().getAttribute(SUPPOSED_TIME_TO_PARAM);
        }

        String dateFrom = supposedDateFrom + " " + supposedTimeFrom;
        String dateTo = supposedDateTo + " " + supposedTimeTo;

        if (!validator.validateDate(dateFrom, dateTo)) {
            request.setAttribute(INVALID_DATE_PARAM, true);
            return PageName.ALL_CARS;
        }

        try {
            cars = service.takeCarsByTypeAndDate(carType, dateFrom, dateTo, pageNumber, AMOUNT_CARS_ON_PAGE);
            if (cars.size() == 0) {
                request.setAttribute(NO_CARS_PARAM, true);
            }
            amountPages = service.countPageAmountUnusedTypeCars(carType, AMOUNT_CARS_ON_PAGE, dateFrom, dateTo);
            request.setAttribute(AMOUNT_PAGES_PARAM, amountPages);
            request.setAttribute(ALL_CARS_PARAM, cars);
            request.setAttribute(COMMAND_PARAM, VIEW_TYPE_UNUSED_VALUE);
            request.setAttribute(CAR_TYPE_PARAM, carType);
            LOG.debug(EXECUTE_ENDS);
            return PageName.ALL_CARS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }

    }
}
