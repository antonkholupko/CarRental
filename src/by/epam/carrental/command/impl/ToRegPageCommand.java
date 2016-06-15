package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Команда для перехода на страницу регистрации пользователя
 */
public class ToRegPageCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ToRegPageCommand.class.getName());

    private static final String EXECUTE_STARTS = "ToRegPageCommand : execute : starts";
    private static final String EXECUTE_ENDS = "ToRegPageCommand : execute : ends";

    private static final String PROCESS_REQUEST_PARAM = "processRequest";

    private static final String FORWARD_VALUE = "forward";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
        LOG.debug(EXECUTE_ENDS);
        return PageName.REGISTRATION_PAGE;
    }
}
