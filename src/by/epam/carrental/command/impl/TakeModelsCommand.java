package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.service.CarService;
import by.epam.carrental.service.Validator;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Команда для получения всех моделей автомобилей
 */
public class TakeModelsCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ToAddCarCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("TakeModelsCommand : execute");
        String mark = (String) request.getParameter("carMark");
        List<String> models = null;
        CarService service = CarService.getInstance();
        Validator validator = Validator.getInstance();
        try {
            if (validator.validateMark(mark)) {
                models = service.takeModels(mark);
                request.getSession().setAttribute("carMark", mark);
                request.getSession().setAttribute("models", models);
                return PageName.ADD_CAR;
            } else {
                request.setAttribute("invalidMark", true);
                return PageName.ADD_CAR;
            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
