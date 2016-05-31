package by.epam.carrental.command;

import by.epam.carrental.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    public String execute(HttpServletRequest request) throws CommandException;
}
