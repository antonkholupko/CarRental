package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Команда для выхода пользователя из системы
 */
public class LogOutCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(LogOutCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("LogOutCommand : execute");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return PageName.INDEX_PAGE;
    }
}
