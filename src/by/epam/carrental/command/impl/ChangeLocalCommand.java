package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Команда для смены локали
 */
public class ChangeLocalCommand implements Command{

    private static final Logger LOG = LogManager.getLogger(ChangeLocalCommand.class.getName());

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("Command : change-locale");
        String language = request.getParameter("language");
        if (language.isEmpty() || language == null) {
            language = "ru";
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("locale", language);
        if (session.getAttribute("type") != null) {
            if (session.getAttribute("type").equals("user")) {
                return PageName.INDEX_PAGE;
            } else if(session.getAttribute("type").equals("admin")) {
                return PageName.INDEX_PAGE;
            } else {
                return PageName.INDEX_PAGE;
            }
        } else {
            return PageName.INDEX_PAGE;
        }
    }
}
