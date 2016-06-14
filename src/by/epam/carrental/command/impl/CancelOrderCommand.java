package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.service.OrderService;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Команда для отмены заказа пользователем
 * <p>
 *     Класс CancelOrderCommand представляет команду для отмены закза пользователем.
 * </p>
 */
public class CancelOrderCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(CancelOrderCommand.class.getName());

    private static final String EXECUTE_STARTS_MSG = "CancelOrderCommand : execute : starts";
    private static final String EXECUTE_ENDS_MSG = "CancelOrderCommand : execute : ends";

    private static final String STATUS = "canceled";

    private static final String PROCESS_REQUEST_PARAM = "processRequest";
    private static final String SELECTED_ORDER_ID_PARAM = "selectedOrderId";
    private static final String SUCCESSFUL_CANCELED = "successfulCanceled";

    private static final String REDIRECT_VALUE = "redirect";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS_MSG);
        int orderId = (Integer) request.getSession().getAttribute(SELECTED_ORDER_ID_PARAM);
        OrderService service = OrderService.getInstance();
        try {
            service.updateStatusById(STATUS, orderId);
            request.getSession().setAttribute(SUCCESSFUL_CANCELED, true);
            request.setAttribute(PROCESS_REQUEST_PARAM, REDIRECT_VALUE);
            LOG.debug(EXECUTE_ENDS_MSG);
            return PageName.USER_SUCCESS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
