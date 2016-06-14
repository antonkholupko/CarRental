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

    private static final String SELECTED_CAR_ID_PARAM = "selectedCarId";
    private static final String SUCCESSFUL_DELETED = "carSuccessfulDeleted";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int selectedCarId = (Integer) request.getSession().getAttribute(SELECTED_CAR_ID_PARAM);
        CarService service = CarService.getInstance();
        try {
            service.deleteCar(selectedCarId);
            request.getSession().setAttribute(SUCCESSFUL_DELETED, true);
            request.setAttribute("processRequest", "redirect");
            return PageName.ADMIN_SUCCESS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
