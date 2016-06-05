package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.Car;
import by.epam.carrental.entity.CarType;
import by.epam.carrental.service.CarService;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Команда для получения всех автомобилей
 */
public class ViewAllCarsCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ViewAllCarsCommand.class.getName());

    private static  final int AMOUNT_CARS_ON_PAGE = 9;

    private static final String EXECUTE_STARTS = "ViewAllCarsCommand : execute";
    private static final String PAGE_NUMBER_PARAM = "pageNumber";
    private static final String AMOUNT_PAGES_PARAM = "amountPages";
    private static final String ALL_CARS_PARAM = "allCars";
    private static final String ALL_TYPES_PARAM = "allTypes";
    private static final String NO_CARS_PARAM = "noCars";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int amountPages = 0;
        int pageNumber = 1;
        if (request.getParameter(PAGE_NUMBER_PARAM) != null) {
            pageNumber = Integer.parseInt(request.getParameter(PAGE_NUMBER_PARAM));
        }
        List<Car> cars = null;
        List<CarType> carTypes = null;
        CarService service = CarService.getInstance();
        try {
            cars = service.takeAllCars(pageNumber, AMOUNT_CARS_ON_PAGE);
            if (cars.size() == 0) {
                request.setAttribute(NO_CARS_PARAM, true);
            }
            carTypes = service.takeCarTypes();
            amountPages = service.countPageAmountAllCars(AMOUNT_CARS_ON_PAGE);
            request.setAttribute(AMOUNT_PAGES_PARAM, amountPages);
            request.getSession().setAttribute(ALL_CARS_PARAM, cars);
            request.getSession().setAttribute(ALL_TYPES_PARAM, carTypes);
            request.setAttribute(PAGE_NUMBER_PARAM, pageNumber);
            return PageName.ALL_CARS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
