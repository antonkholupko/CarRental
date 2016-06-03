package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.Car;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Команда для получения информации об одном автомобиле
 */
public class ViewCarCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(ViewCarCommand.class.getName());
    private static final String EXECUTE_STARTS = "ViewCarCommand : execute";
    private static final String SELECTED_CAR_ID_PARAM = "selectedCarId";
    private static final String ALL_CARS_PARAM = "allCars";
    private static final String SELECTED_CAR_PARAM = "selectedCar";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int id = Integer.valueOf(request.getParameter(SELECTED_CAR_ID_PARAM));
        for (Car car : (List<Car>) request.getSession().getAttribute(ALL_CARS_PARAM)){
            if (car.getId() == id){
                request.getSession().setAttribute(SELECTED_CAR_ID_PARAM, id);
                request.getSession().setAttribute(SELECTED_CAR_PARAM, car);
                break;
            }
        }
        return PageName.VIEW_CAR;
    }
}
