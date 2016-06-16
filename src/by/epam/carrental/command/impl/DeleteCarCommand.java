package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.service.CarService;
import by.epam.carrental.service.OrderService;
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
    private static final String CANNOT_DELETE_PARAM = "cannotDelete";
    private static final String PROCESS_REQUEST_PARAM = "processRequest";
    private static final String REDIRECT_VALUE = "redirect";
    private static final String FORWARD_VALUE = "forward";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int selectedCarId = (Integer) request.getSession().getAttribute(SELECTED_CAR_ID_PARAM);
        CarService carService = CarService.getInstance();
        OrderService orderService = OrderService.getInstance();
        try {
            if (orderService.isMayDelete(selectedCarId)) {
                carService.deleteCar(selectedCarId);
                request.getSession().setAttribute(SUCCESSFUL_DELETED_PARAM, true);
                request.setAttribute(PROCESS_REQUEST_PARAM, REDIRECT_VALUE);
                LOG.debug(EXECUTE_ENDS);
                return PageName.ADMIN_SUCCESS;
            } else {
                request.setAttribute(CANNOT_DELETE_PARAM, true);
                request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
                LOG.debug(EXECUTE_ENDS);
                return PageName.VIEW_CAR;
            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
