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
    private static  final int AMOUNT_CARS_ON_PAGE = 9;

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("ViewAllUnusedCarsCommand : execute");
        int amountPages = 0;
        int pageNumber = 1;

        String carType = (String) request.getSession().getAttribute("carType");
        String supposedDateFrom = request.getParameter("supposedDateFrom") + " " + request.getParameter("supposedTimeFrom");
        String supposedDateTo = request.getParameter("supposedDateTo") + " " + request.getParameter("supposedTimeTo");
        request.getSession().setAttribute("supposedDateFrom", request.getParameter("supposedDateFrom"));
        request.getSession().setAttribute("supposedTimeFrom", request.getParameter("supposedTimeFrom"));
        request.getSession().setAttribute("supposedDateTo", request.getParameter("supposedDateTo"));
        request.getSession().setAttribute("supposedTimeTo", request.getParameter("supposedTimeTo"));
        Validator validator = Validator.getInstance();
        List<Car> cars = null;
        CarService service = CarService.getInstance();
        if (!validator.validateDate(supposedDateFrom, supposedDateTo)) {
            request.setAttribute("invalidDate", true);
            return PageName.ALL_CARS;
        }
        try {
            cars = service.takeUnusedCars(supposedDateFrom, supposedDateTo, pageNumber, AMOUNT_CARS_ON_PAGE);
            amountPages = service.countPageAmountAllCars(AMOUNT_CARS_ON_PAGE);
            request.setAttribute("amountPages", amountPages);
            request.setAttribute("allCars", cars);
            return PageName.ALL_CARS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
