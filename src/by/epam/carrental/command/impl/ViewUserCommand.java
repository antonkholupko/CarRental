package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.User;
import by.epam.carrental.service.UserService;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Команда получает информацию о пользователе
 */
public class ViewUserCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ViewUserCommand.class.getName());

    private static final String EXECUTE_STARTS = "ViewUserCommand : execute : starts";
    private static final String EXECUTE_ENDS = "ViewUserCommand : execute : ends";

    private static final String SELECTED_USER_ID_PARAM = "selectedUserId";
    private static final String SELECTED_USER_PARAM = "selectedUser";
    private static final String PROCESS_REQUEST = "processRequest";

    private static final String FORWARD_VALUE = "forward";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int userId = Integer.parseInt(request.getParameter(SELECTED_USER_ID_PARAM));
        UserService service = UserService.getInstance();
        try {
            User user = service.findUserById(userId);
            request.setAttribute(SELECTED_USER_PARAM, user);
            request.setAttribute(PROCESS_REQUEST, FORWARD_VALUE);
            LOG.debug(EXECUTE_ENDS);
            return PageName.USER;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
