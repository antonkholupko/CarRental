package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.CarType;
import by.epam.carrental.service.CarService;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Команда для добавления автомобиля
 */
public class ToAddCarCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ToAddCarCommand.class.getName());
    private static final String EXECUTE_STARTS = "ToAddCarCommand  : execute";
    private static final String ALL_CAR_MARKS_PARAM = "allCarMarks";
    private static final String ALL_CAR_TYPES_PARAM = "allCarTypes";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        List<String> marks = null;
        List<CarType> types = null;
        CarService service = CarService.getInstance();
        try {
            marks = service.takeMarks();
            types = service.takeCarTypes();
            request.getSession().setAttribute(ALL_CAR_MARKS_PARAM, marks);
            request.getSession().setAttribute(ALL_CAR_TYPES_PARAM, types);
            return PageName.ADD_CAR;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
