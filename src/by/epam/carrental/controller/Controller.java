package by.epam.carrental.controller;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.CommandName;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.controller.helper.CommandHelper;
import by.epam.carrental.entity.Car;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@MultipartConfig
public class Controller extends HttpServlet {
    private static final Logger LOG = LogManager.getLogger(Controller.class.getName());
    private static final long serialVersionUID = 1L;
    private static final String COMMAND_PARAMETER = "command";
    private static final String DO_GET_MSG = "Controller : doGet";
    private static final String DO_POST_MSG = "Controller : doPost";
    private static final String PROCESS_REQUEST_MSG = "Controller : processRequest";
    private static final String ERROR_MSG = "Controller : ERROR : ";


    public Controller() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug(DO_GET_MSG);
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug(DO_POST_MSG);
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        LOG.debug(PROCESS_REQUEST_MSG);
        String commandName = null;
        Command command = null;
        String page = PageName.INDEX_PAGE;

        try {

            if (request.getParameter(COMMAND_PARAMETER) != null) {
                commandName = request.getParameter(COMMAND_PARAMETER);
                command = CommandHelper.getInstance().getCommand(commandName);
                page = command.execute(request);
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            if (dispatcher != null) {
                dispatcher.forward(request, response);
            } else {
                page = PageName.ERROR_PAGE;
                dispatcher = request.getRequestDispatcher(page);
                dispatcher.forward(request, response);
            }
        } catch (CommandException ex) {
            LOG.error(ERROR_MSG, ex);
            page = PageName.ERROR_PAGE;
            RequestDispatcher dispatcher = request.getRequestDispatcher(page);
            dispatcher.forward(request, response);
        }
    }

}
