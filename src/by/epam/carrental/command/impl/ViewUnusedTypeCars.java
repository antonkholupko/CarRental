package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.Car;
import by.epam.carrental.service.CarService;
import by.epam.carrental.service.Validator;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class ViewUnusedTypeCars implements Command {

    private static final Logger LOG = LogManager.getLogger(UpdateRealDateFromCommand.class.getName());
    private static  final int AMOUNT_CARS_ON_PAGE = 9;

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("ViewUnusedTypeCars : execute : starts");
        int amountPages = 0;
        int pageNumber = 1;
        if (request.getParameter("pageNumber") != null) {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        List<Car> cars = null;
        Validator validator = Validator.getInstance();
        CarService service = CarService.getInstance();
        String carType = null;
        if (request.getParameter("carType") != null) {
            carType = request.getParameter("carType");
            request.getSession().setAttribute("carType", carType);
        } else if (request.getSession().getAttribute("carType") != null) {
            carType = (String) request.getSession().getAttribute("carType");
        } else {
            request.setAttribute("invalidType", true);
        }
        String supposedDateFrom = null;
        String supposedTimeFrom = null;
        String supposedDateTo = null;
        String supposedTimeTo = null;

        if (request.getParameter("supposedDateFrom") != null) {
            supposedDateFrom = request.getParameter("supposedDateFrom");
            supposedTimeFrom = request.getParameter("supposedTimeFrom");
            supposedDateTo = request.getParameter("supposedDateTo");
            supposedTimeTo = request.getParameter("supposedTimeTo");

            request.getSession().setAttribute("supposedDateFrom", supposedDateFrom);
            request.getSession().setAttribute("supposedTimeFrom", supposedTimeFrom);
            request.getSession().setAttribute("supposedDateTo", supposedDateTo);
            request.getSession().setAttribute("supposedTimeTo", supposedTimeTo);

        } else {
            supposedDateFrom = (String) request.getSession().getAttribute("supposedDateFrom");
            supposedTimeFrom = (String) request.getSession().getAttribute("supposedTimeFrom");
            supposedDateTo = (String) request.getSession().getAttribute("supposedDateTo");
            supposedTimeTo = (String) request.getSession().getAttribute("supposedTimeTo");
        }

        String dateFrom = supposedDateFrom + " " + supposedTimeFrom;
        String dateTo = supposedDateTo + " " + supposedTimeTo;

        if (!validator.validateDate(dateFrom, dateTo)) {
            request.setAttribute("invalidDate", true);
            return PageName.ALL_CARS;
        }

        try {
            cars = service.takeCarsByTypeAndDate(carType, dateFrom, dateTo, pageNumber, AMOUNT_CARS_ON_PAGE);
            amountPages = service.countPageAmountUnusedTypeCars(carType, AMOUNT_CARS_ON_PAGE, dateFrom, dateTo);
            request.setAttribute("amountPages", amountPages);
            request.setAttribute("allCars", cars);
            request.setAttribute("command", "view-type-unused");
            request.setAttribute("carType", carType);
            LOG.debug("ViewUnusedTypeCars : execute : ends");
            return PageName.ALL_CARS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }

    }
}
