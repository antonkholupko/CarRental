package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.Car;
import by.epam.carrental.entity.User;
import by.epam.carrental.service.Validator;
import by.epam.carrental.service.CarService;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Команда получает список автомобилей определенного типа
 */
public class ViewTypeByDateCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ViewTypeByDateCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("ViewTypeCommand : execute");
        User user = (User) request.getSession(true).getAttribute("user");
        String type = request.getParameter("carType");
        String supposedDateFrom = null;
        String supposedDateTo = null;
        request.getSession().setAttribute("carType", type);
        if (request.getSession().getAttribute("supposedDateFrom") != null && request.getSession().getAttribute("supposedTimeFrom") != null
                && request.getSession().getAttribute("supposedDateTo") != null && request.getSession().getAttribute("supposedTimeTo") != null) {
            supposedDateFrom = request.getSession(true).getAttribute("supposedDateFrom") + " " + request.getSession(true).getAttribute("supposedTimeFrom");
            supposedDateTo = request.getSession(true).getAttribute("supposedDateTo") + " " + request.getSession(true).getAttribute("supposedTimeTo");
        }
        CarService service = CarService.getInstance();
        Validator validator = Validator.getInstance();
        List<Car> cars = null;
        try {
            if (supposedDateFrom != null && supposedDateTo != null && !validator.validateDate(supposedDateFrom, supposedDateTo)) {
                request.setAttribute("invalidDate", true);
                return PageName.ALL_CARS;
            } else {
                cars = service.takeCarsByTypeAndDate(type, supposedDateFrom, supposedDateTo);
                request.getSession().setAttribute("allCars", cars);
                return PageName.ALL_CARS;
            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
