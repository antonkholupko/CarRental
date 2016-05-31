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

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("DeleteCarCommand : execute");
        request.setAttribute("commandName", "delete-car");
        int amountPages = 0;
        int pageNumber = 1;
        List<Car> cars = null;
        List<CarType> carTypes = null;
        int selectedCarId = (Integer) request.getSession().getAttribute("selectedCarId");
        if (request.getParameter("pageNumber") != null) {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        CarService service = CarService.getInstance();
        try {
            service.deleteCar(selectedCarId);
            cars = service.takeAllCars(pageNumber, AMOUNT_CARS_ON_PAGE);
            carTypes = service.takeCarTypes();
            amountPages = service.countPageAmountAllCars(AMOUNT_CARS_ON_PAGE);
            request.setAttribute("amountPages", amountPages);
            request.getSession().setAttribute("allCars", cars);
            request.getSession().setAttribute("allTypes", carTypes);
            request.setAttribute("carSuccessfulDeleted", true);
            return PageName.ALL_CARS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
