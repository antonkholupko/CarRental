package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Команда для перехода на страницу оплаты заказа
 */
public class ToPaymentCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        request.getSession().setAttribute("paymentType", request.getParameter("payment-type"));
        return PageName.PAYMENT;
    }
}
