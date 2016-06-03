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
    private static final String EXECUTE_STARTS = "ToRegPageCommand : execute";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        return PageName.REGISTRATION_PAGE;
    }
}
