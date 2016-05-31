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
public class ViewUserCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(ViewUserCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("ViewUserCommand : execute");
        int userId = Integer.parseInt(request.getParameter("selectedUserId"));
        UserService service = UserService.getInstance();
        try {
            User user = service.findUserById(userId);
            request.getSession().setAttribute("selectedUser", user);
            return PageName.USER;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
