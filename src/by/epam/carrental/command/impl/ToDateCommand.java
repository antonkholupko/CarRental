package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ToDateCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(ToDateCommand.class.getName());

    private static final String EXECUTE_STARTS_MSG = "ToDateCommand : execute : starts";
    private static final String EXECUTE_ENDS_MSG = "ToDateCommand : execute : ends";

    private static final String SELECTED_ORDER_ID_PARAM = "selectedOrderId";
    private static final String SET_DATE_FROM_PARAM = "setDateFrom";
    private static final String FROM_DATE_PARAM = "fromDate";
    private static final String SET_DATE_TO_PARAM = "setDateTo";
    private static final String DATE_TO_PARAM = "toDate";
    private static final String ORDER_ID_PARAM = "orderId";
    private static final String PROCESS_REQUEST_PARAM = "processRequest";

    private static final String REDIRECT_VALUE = "redirect";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS_MSG);
        int orderId = Integer.parseInt(request.getParameter(SELECTED_ORDER_ID_PARAM));
        if (Boolean.parseBoolean(request.getParameter(SET_DATE_FROM_PARAM))) {
            request.getSession().setAttribute(FROM_DATE_PARAM, true);
        }
        if (Boolean.parseBoolean(request.getParameter(SET_DATE_TO_PARAM))) {
            request.getSession().setAttribute(DATE_TO_PARAM, true);
        }
        request.getSession().setAttribute(ORDER_ID_PARAM, orderId);
        request.setAttribute(PROCESS_REQUEST_PARAM, REDIRECT_VALUE);
        LOG.debug(EXECUTE_ENDS_MSG);
        return PageName.DATE;
    }
}
