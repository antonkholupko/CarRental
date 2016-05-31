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
import java.util.List;

public class ViewTypeCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ViewTypeCommand.class.getName());

    private static  final int AMOUNT_CARS_ON_PAGE = 9;

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("ViewTypeCommand : execute : starts");
        int amountPages = 0;
        int pageNumber = 1;
        if (request.getParameter("pageNumber") != null) {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        String type = request.getParameter("carType");
        CarService service = CarService.getInstance();
        List<Car> cars = null;
        try {
            cars = service.takeCarsByType(type, pageNumber, AMOUNT_CARS_ON_PAGE);
            amountPages = service.countPageAmountTypeCars(type, AMOUNT_CARS_ON_PAGE);
            request.setAttribute("amountPages", amountPages);
            request.setAttribute("allCars", cars);
            request.setAttribute("command", "view-type");
            request.setAttribute("carType", type);
            LOG.debug("ViewTypeCommand : execute : ends");
            return PageName.ALL_CARS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
