package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.Car;
import by.epam.carrental.service.CarService;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Команда для получения информации об одном автомобиле
 */
public class ViewCarCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(ViewCarCommand.class.getName());
    private static final String EXECUTE_STARTS = "ViewCarCommand : execute";
    private static final String SELECTED_CAR_ID_PARAM = "selectedCarId";
    private static final String SELECTED_CAR_PARAM = "selectedCar";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int id = Integer.valueOf(request.getParameter(SELECTED_CAR_ID_PARAM));
        CarService service = CarService.getInstance();
        Car car = null;
        try {
            car = service.takeCarById(id);
            request.getSession().setAttribute(SELECTED_CAR_ID_PARAM, id);
            request.getSession().setAttribute(SELECTED_CAR_PARAM, car);
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
        return PageName.VIEW_CAR;
    }
}
