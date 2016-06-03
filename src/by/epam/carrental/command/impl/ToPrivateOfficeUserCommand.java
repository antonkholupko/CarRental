package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Команда для перехода в личный кабинет пользователя
 */
public class ToPrivateOfficeUserCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ToPrivateOfficeUserCommand.class.getName());
    private static final String EXECUTE_STARTS = "ToPrivateOfficeUserCommand : execute";


    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS);
        return PageName.PRIV_OFF_USER;
    }
}
