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

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("ViewCarCommand : execute");
        int id = Integer.valueOf(request.getParameter("selectedCarId"));
        for (Car car : (List<Car>) request.getSession().getAttribute("allCars")){
            if (car.getId() == id){
                request.getSession().setAttribute("selectedCarId", id);
                request.getSession().setAttribute("selectedCar", car);
                break;
            }
        }
        return PageName.VIEW_CAR;
    }
}
