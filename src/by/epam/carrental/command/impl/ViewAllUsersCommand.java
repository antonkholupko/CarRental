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
import java.util.List;

/**
 * Команда получает список всех пользователей
 */
public class ViewAllUsersCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ViewAllUsersCommand.class.getName());
    private static final String EXECUTE_STARTS = "ViewAllUsersCommand : execute";
    private static final String ALL_USERS_PARAM = "allUsers";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        UserService service = UserService.getInstance();
        try {
            List<User> users = service.takeAllUsers();
            request.getSession().setAttribute(ALL_USERS_PARAM, users);
            return PageName.ALL_USERS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
