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

    private static final String EXECUTE_STARTS_MSG = "TakeModelsCommand : execute : starts";
    private static final String EXECUTE_ENDS_MSG = "TakeModelsCommand : execute : ends";

    private static final String CAR_MARK_PARAM = "carMark";
    private static final String MODELS_PARAM = "models";
    private static final String INVALID_MARK_PARAM = "invalidMark";
    private static final String PROCESS_REQUEST_PARAM = "processRequest";

    private static final String FORWARD_VALUE = "forward";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS_MSG);
        String mark = request.getParameter(CAR_MARK_PARAM);
        List<String> models = null;
        CarService service = CarService.getInstance();
        Validator validator = Validator.getInstance();
        try {
            if (validator.validateMark(mark)) {
                models = service.takeModels(mark);
                request.getSession().setAttribute(CAR_MARK_PARAM, mark);
                request.getSession().setAttribute(MODELS_PARAM, models);
                request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
                LOG.debug(EXECUTE_ENDS_MSG);
                return PageName.ADD_CAR;
            } else {
                request.setAttribute(INVALID_MARK_PARAM, true);
                request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
                LOG.debug(EXECUTE_ENDS_MSG);
                return PageName.ADD_CAR;
            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
