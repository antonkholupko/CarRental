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

    private static final String EXECUTE_STARTS_MSG = "ChangeLocalCommand : execute : starts";
    private static final String EXECUTE_ENDS_MSG = "ChangeLocalCommand : execute : ends";

    private static final String LANGUAGE_PARAM = "language";
    private static final String LOCALE_PARAM = "locale";
    private static final String TYPE_PARAM = "type";
    private static final String PROCESS_REQUEST_PARAM = "processRequest";

    private static final String FORWARD_VALUE = "forward";
    private static final String RU_VALUE = "ru";
    private static final String USER_VALUE = "user";
    private static final String ADMIN_USER = "admin";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS_MSG);
        String language = request.getParameter(LANGUAGE_PARAM);
        if (language.isEmpty() || language == null) {
            language = RU_VALUE;
        }
        HttpSession session = request.getSession(true);
        session.setAttribute(LOCALE_PARAM, language);
        if (session.getAttribute(TYPE_PARAM) != null) {
            if (session.getAttribute(TYPE_PARAM).equals(USER_VALUE)) {
                request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
                LOG.debug(EXECUTE_ENDS_MSG);
                return PageName.INDEX_PAGE;
            } else if(session.getAttribute(TYPE_PARAM).equals(ADMIN_USER)) {
                request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
                LOG.debug(EXECUTE_ENDS_MSG);
                return PageName.INDEX_PAGE;
            } else {
                request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
                LOG.debug(EXECUTE_ENDS_MSG);
                return PageName.INDEX_PAGE;
            }
        } else {
            request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
            LOG.debug(EXECUTE_ENDS_MSG);
            return PageName.INDEX_PAGE;
        }
    }
}
