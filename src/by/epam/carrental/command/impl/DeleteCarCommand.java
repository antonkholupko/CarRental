package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.service.CarService;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Команда для удаления автомобиля
 *
 * <p>
 *     Класс DeleteCarCommand - для удаления автомобиля пользователем
 * </p>
 */
public class DeleteCarCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(DeleteCarCommand.class.getName());

    private static final String EXECUTE_STARTS = "DeleteCarCommand : execute : starts";
    private static final String EXECUTE_ENDS = "DeleteCarCommand : execute : ends";

    private static final String SELECTED_CAR_ID_PARAM = "selectedCarId";
    private static final String SUCCESSFUL_DELETED_PARAM = "carSuccessfulDeleted";
    private static final String PROCESS_REQUEST_PARAM = "processRequest";
    private static final String REDIRECT_VALUE = "redirect";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int selectedCarId = (Integer) request.getSession().getAttribute(SELECTED_CAR_ID_PARAM);
        CarService service = CarService.getInstance();
        try {
            service.deleteCar(selectedCarId);
            request.getSession().setAttribute(SUCCESSFUL_DELETED_PARAM, true);
            request.setAttribute(PROCESS_REQUEST_PARAM, REDIRECT_VALUE);
            LOG.debug(EXECUTE_ENDS);
            return PageName.ADMIN_SUCCESS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
