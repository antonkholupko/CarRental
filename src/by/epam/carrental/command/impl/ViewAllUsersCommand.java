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

    private static final String EXECUTE_STARTS = "ViewAllUsersCommand : execute : starts";
    private static final String EXECUTE_ENDS = "ViewAllUsersCommand : execute : ends";

    private static final String ALL_USERS_PARAM = "allUsers";
    private static final String PAGE_NUMBER_PARAM = "pageNumber";
    private static final String AMOUNT_PAGES_PARAM = "amountPages";
    private static final String NO_USERS_PARAM = "noUsers";
    private static final String PROCESS_REQUEST_PARAM = "processRequest";

    private static final int AMOUNT_USERS_ON_PAGE = 4;
    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final String FORWARD_VALUE = "forward";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        int amountPages = 0;
        int pageNumber = DEFAULT_PAGE_NUMBER;
        UserService service = UserService.getInstance();
        if (request.getParameter(PAGE_NUMBER_PARAM) != null) {
            pageNumber = Integer.parseInt(request.getParameter(PAGE_NUMBER_PARAM));
        }
        try {
            List<User> users = service.takeAllUsers(pageNumber, AMOUNT_USERS_ON_PAGE);
            if (users.size() == 0) {
                request.setAttribute(NO_USERS_PARAM, true);
            }
            amountPages = service.countPageAmountAllUsers(AMOUNT_USERS_ON_PAGE);
            request.setAttribute(AMOUNT_PAGES_PARAM, amountPages);
            request.setAttribute(PAGE_NUMBER_PARAM, pageNumber);
            request.getSession().setAttribute(ALL_USERS_PARAM, users);
            request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
            LOG.debug(EXECUTE_ENDS);
            return PageName.ALL_USERS;
        } catch (ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
