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
    private static final String EXECUTE_STARTS = "ViewTypeCommand : execute : starts";
    private static final String EXECUTE_ENDS = "ViewTypeCommand : execute : ends";
    private static final String PAGE_NAUMER_PARAM = "pageNumber";
    private static final String CAR_TYPE_PARAM = "carType";
    private static final String AMOUNT_PAGES_PARAM = "amountPages";
    private static final String ALL_CARS_PARAM = "allCars";
    private static final String COMMAND_PARAM = "command";
    private static final String COMMAND_VALUE = "view-type";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int amountPages = 0;
        int pageNumber = 1;
        if (request.getParameter(PAGE_NAUMER_PARAM) != null) {
            pageNumber = Integer.parseInt(request.getParameter(PAGE_NAUMER_PARAM));
        }
        String type = request.getParameter(CAR_TYPE_PARAM);
        CarService service = CarService.getInstance();
        List<Car> cars = null;
        try {
            cars = service.takeCarsByType(type, pageNumber, AMOUNT_CARS_ON_PAGE);
            amountPages = service.countPageAmountTypeCars(type, AMOUNT_CARS_ON_PAGE);
            request.setAttribute(AMOUNT_PAGES_PARAM, amountPages);
            request.setAttribute(ALL_CARS_PARAM, cars);
            request.setAttribute(COMMAND_PARAM, COMMAND_VALUE);
            request.setAttribute(CAR_TYPE_PARAM, type);
            LOG.debug(EXECUTE_ENDS);
            return PageName.ALL_CARS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
