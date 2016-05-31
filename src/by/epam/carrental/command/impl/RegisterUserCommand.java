package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.service.Validator;
import by.epam.carrental.entity.ValidatorUniqueUser;
import by.epam.carrental.service.UserService;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * Команда для регистрации пользователя
 */
public class RegisterUserCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(RegisterUserCommand.class.getName());
    private static final String LOGIN_PARAMETER = "login";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String CONFIRM_PASSWORD_PARAMETER = "confirm-password";
    private static final String E_MAIL = "e-mail";
    private static final String LAST_NAME = "last-name";
    private static final String FIRST_NAME = "first-name";
    private static final String MIDDLE_NAME = "middle-name";
    private static final String PHONE = "phone";
    private static final String PASSPORT = "passport";
    private static final String ADDRESS = "address";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        LOG.debug("RegisterUserCommand : execute");
        if (validParameters(request)) {
            return PageName.INDEX_PAGE;
        } else {
            return PageName.REGISTRATION_PAGE;
        }
    }



    private boolean validParameters(HttpServletRequest request) throws CommandException {
        LOG.debug("RegisterUserCommand : validParameters");
        String login = request.getParameter(LOGIN_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD_PARAMETER);
        String eMail = request.getParameter(E_MAIL);
        String lastName = request.getParameter(LAST_NAME);
        String firstName = request.getParameter(FIRST_NAME);
        String middleName = request.getParameter(MIDDLE_NAME);
        String phone = request.getParameter(PHONE);
        String passport = request.getParameter(PASSPORT);
        String address = request.getParameter(ADDRESS);

        boolean uniqueLogin = false;
        boolean uniqueEmail = false;
        boolean uniquePassport = false;

        int countFailedValidations = 0;

        Validator validator = Validator.getInstance();

        request.setAttribute("validLogin", true);
        request.setAttribute("validPassword", true);
        request.setAttribute("validConfirmPassword", true);
        request.setAttribute("confirmationPassword", true);
        request.setAttribute("validEmail", true);
        request.setAttribute("validLastName", true);
        request.setAttribute("validFirstName", true);
        request.setAttribute("validMiddleName", true);
        request.setAttribute("validPhone", true);
        request.setAttribute("validPassport", true);
        request.setAttribute("validAddress", true);

        if (!validator.validateLogin(login)) {
            request.setAttribute("validLogin", false);
            countFailedValidations++;
        }
        if (!validator.validatePassword(password)) {
            request.setAttribute("validPassword", false);
            countFailedValidations++;
        }
        if (!validator.validatePassword(confirmPassword)) {
            request.setAttribute("validConfirmPassword", false);
            countFailedValidations++;
        }
        if (!validator.validateConfirmationPassword(password, confirmPassword)) {
            request.setAttribute("confirmationPassword", false);
            countFailedValidations++;
        }
        if (!validator.validateEmail(eMail)) {
            request.setAttribute("validEmail", false);
            countFailedValidations++;
        }
        if (!validator.validateLastName(lastName)) {
            request.setAttribute("validLastName", false);
            countFailedValidations++;
        }
        if (!validator.validateFirstName(firstName)) {
            request.setAttribute("validFirstName", false);
            countFailedValidations++;
        }
        if (!validator.validateMiddleName(middleName)) {
            request.setAttribute("validMiddleName", false);
            countFailedValidations++;
        }
        if (!validator.validatePhone(phone)) {
            request.setAttribute("validPhone", false);
            countFailedValidations++;
        }
        if (!validator.validatePassport(passport)) {
            request.setAttribute("validPassport", false);
            countFailedValidations++;
        }
        if (!validator.validateAddress(address)) {
            request.setAttribute("validAddress", false);
            countFailedValidations++;
        }

        if (countFailedValidations > 0) {
            return false;
        } else {
            try {
                UserService service = UserService.getInstance();
                ValidatorUniqueUser validatorUniqueUser = service.register(login, password.hashCode(), lastName,
                        firstName, middleName, eMail, phone, passport, address);
                uniqueLogin = validatorUniqueUser.isUniqueLogin();
                uniqueEmail = validatorUniqueUser.isUniqueEmail();
                uniquePassport = validatorUniqueUser.isUniquePassport();
                request.setAttribute("uniqueLogin", uniqueLogin);
                request.setAttribute("uniqueEmail", uniqueEmail);
                request.setAttribute("uniquePassport", uniquePassport);
                if (!(uniqueLogin && uniqueEmail && uniquePassport)) {
                    return false;
                }
            } catch (ServiceException ex) {
                throw new CommandException(ex);
            }
        }
        return true;
    }
}
