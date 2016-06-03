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
 * Команда для удаления автомобиля
 *
 * <p>
 *     Класс DeleteCarCommand - для удаления автомобиля пользователем
 * </p>
 */
public class DeleteCarCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(DeleteCarCommand.class.getName());

    private static  final int AMOUNT_CARS_ON_PAGE = 9;

    private static final String EXECUTE_STARTS = "DeleteCarCommand : execute : starts";

    private static final String COMMAND_NAME_PARAM = "commandName";
    private static final String SELECTED_CAR_ID_PARAM = "selectedCarId";
    private static final String PAGE_NUMBER_PARAM = "pageNumber";
    private static final String ALL_CARS_PARAM = "allCars";
    private static final String ALL_TYPES_PARAM = "allTypes";
    private static final String AMOUNT_PAGES_PARAM = "amountPages";
    private static final String SUCCESSFUL_DELETED = "carSuccessfulDeleted";

    private static final String DELETE_CAR_VALUE = "delete-car";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        request.setAttribute(COMMAND_NAME_PARAM, DELETE_CAR_VALUE);
        int amountPages = 0;
        int pageNumber = 1;
        List<Car> cars = null;
        List<CarType> carTypes = null;
        int selectedCarId = (Integer) request.getSession().getAttribute(SELECTED_CAR_ID_PARAM);
        if (request.getParameter(PAGE_NUMBER_PARAM) != null) {
            pageNumber = Integer.parseInt(request.getParameter(PAGE_NUMBER_PARAM));
        }
        CarService service = CarService.getInstance();
        try {
            service.deleteCar(selectedCarId);
            cars = service.takeAllCars(pageNumber, AMOUNT_CARS_ON_PAGE);
            carTypes = service.takeCarTypes();
            amountPages = service.countPageAmountAllCars(AMOUNT_CARS_ON_PAGE);
            request.setAttribute(AMOUNT_PAGES_PARAM, amountPages);
            request.getSession().setAttribute(ALL_CARS_PARAM, cars);
            request.getSession().setAttribute(ALL_TYPES_PARAM, carTypes);
            request.setAttribute(SUCCESSFUL_DELETED, true);
            return PageName.ALL_CARS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
