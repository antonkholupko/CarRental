package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

/**
 * Команда для перехода на страницу оплаты заказа
 */
public class ToPaymentCommand implements Command {

    private static final String PAYMENT_TYPE_PARAM = "paymentType";
    private static final String PAYMENT_TYPE = "payment-type";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        request.getSession().setAttribute(PAYMENT_TYPE_PARAM, request.getParameter(PAYMENT_TYPE));
        request.setAttribute("processRequest", "forward");
        return PageName.PAYMENT;
    }
}
