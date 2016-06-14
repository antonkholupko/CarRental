package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Команда для перехода на домашнюю страницу приложения
 */
public class ToHomePageCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(ToHomePageCommand.class.getName());

    private static final String EXECUTE_STARTS_MSG = "ToHomePageCommand : execute : starts";
    private static final String EXECUTE_ENDS_MSG = "ToHomePageCommand : execute : ends";

    private static final String PROCESS_REQUEST_PARAM = "processRequest";

    private static final String FORWARD_VALUE = "forward";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS_MSG);
        request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
        LOG.debug(EXECUTE_ENDS_MSG);
        return PageName.INDEX_PAGE;
    }
}
