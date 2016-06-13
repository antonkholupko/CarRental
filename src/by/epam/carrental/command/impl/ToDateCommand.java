package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public class ToDateCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        int orderId = Integer.parseInt(request.getParameter("selectedOrderId"));
        if (Boolean.parseBoolean(request.getParameter("setDateFrom"))) {
            request.getSession().setAttribute("fromDate", true);
        }
        if (Boolean.parseBoolean(request.getParameter("setDateTo"))) {
            request.getSession().setAttribute("toDate", true);
        }
        request.getSession().setAttribute("orderId", orderId);
        request.setAttribute("processRequest", "redirect");
        return PageName.DATE;
    }
}
