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
 * Команда для авторизации пользователя
 * <p>
 *     Класс LoginUserCommand - команда для авторизации пользователя.
 * </p>
 */

public class LoginUserCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(LoginUserCommand.class.getName());
    private static final String PARAM_LOGIN = "login";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_LOGIN_FAILED = "loginFailed";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("LoginUserCommand : execute");
        String login = request.getParameter(PARAM_LOGIN);
        String password = request.getParameter(PARAM_PASSWORD);
        UserService service = UserService.getInstance();
        try {
            User user = service.login(login, password);
            if (user != null) {
                request.getSession(true).setAttribute("user", user);
                if (request.getParameter("page-name").equals("index")) {
                    return PageName.INDEX_PAGE;
                } else if (request.getParameter("page-name").equals("all-cars")) {
                    return PageName.ALL_CARS;
                } else if (request.getParameter("page-name").equals("view-car")) {
                    return PageName.VIEW_CAR;
                } else {
                    return PageName.ERROR_PAGE;
                }
            } else {
                request.setAttribute(PARAM_LOGIN_FAILED, true);
                if (request.getParameter("page-name").equals("index")) {
                    return PageName.INDEX_PAGE;
                } else if (request.getParameter("page-name").equals("all-cars")) {
                    return PageName.ALL_CARS;
                } else if (request.getParameter("page-name").equals("view-car")) {
                    return PageName.VIEW_CAR;
                } else {
                    return PageName.ERROR_PAGE;
                }
            }
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
