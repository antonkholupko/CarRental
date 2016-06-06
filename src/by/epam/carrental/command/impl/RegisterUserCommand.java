package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.ValidatorUniqueUser;
import by.epam.carrental.service.UserService;
import by.epam.carrental.service.Validator;
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
    private static final String EXECUTE_STARTS = "RegisterUserCommand : execute";
    private static final String VALID_PARAM_MSG = "RegisterUserCommand : validParameters";
    private static final String LOGIN_VALID = "validLogin";
    private static final String PASSWORD_VALID = "validPassword";
    private static final String CONFIRM_PASSWORD_VALID = "validConfirmPassword";
    private static final String CONFIRMATION_PASSWORD = "confirmationPassword";
    private static final String E_MAIL_VALID = "validEmail";
    private static final String LAST_NAME_VALID = "validLastName";
    private static final String FIRST_NAME_VALID = "validFirstName";
    private static final String MIDDLE_NAME_VALID = "validMiddleName";
    private static final String PHONE_VALID = "validPhone";
    private static final String PASSPORT_VALID = "validPassport";
    private static final String ADDRESS_VALID = "validAddress";
    private static final String UNIQUE_LOGIN = "uniqueLogin";
    private static final String UNIQUE_EMAIL = "uniqueEmail";
    private static final String UNIQUE_PASSPORT = "uniquePassport";
    private static final String SUCCESSFUL_REGISTER = "successfulRegister";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {

        LOG.debug(EXECUTE_STARTS);
        if (validParameters(request)) {
            request.setAttribute(SUCCESSFUL_REGISTER, true);
            return PageName.INDEX_PAGE;
        } else {
            return PageName.REGISTRATION_PAGE;
        }
    }



    private boolean validParameters(HttpServletRequest request) throws CommandException {
        LOG.debug(VALID_PARAM_MSG);
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

        request.setAttribute(LOGIN_VALID, true);
        request.setAttribute(PASSWORD_VALID, true);
        request.setAttribute(CONFIRM_PASSWORD_VALID, true);
        request.setAttribute(CONFIRMATION_PASSWORD, true);
        request.setAttribute(E_MAIL_VALID, true);
        request.setAttribute(LAST_NAME_VALID, true);
        request.setAttribute(FIRST_NAME_VALID, true);
        request.setAttribute(MIDDLE_NAME_VALID, true);
        request.setAttribute(PHONE_VALID, true);
        request.setAttribute(PASSPORT_VALID, true);
        request.setAttribute(ADDRESS_VALID, true);

        if (!validator.validateLogin(login)) {
            request.setAttribute(LOGIN_VALID, false);
            countFailedValidations++;
        }
        if (!validator.validatePassword(password)) {
            request.setAttribute(PASSWORD_VALID, false);
            countFailedValidations++;
        }
        if (!validator.validatePassword(confirmPassword)) {
            request.setAttribute(CONFIRM_PASSWORD_VALID, false);
            countFailedValidations++;
        }
        if (!validator.validateConfirmationPassword(password, confirmPassword)) {
            request.setAttribute(CONFIRMATION_PASSWORD, false);
            countFailedValidations++;
        }
        if (!validator.validateEmail(eMail)) {
            request.setAttribute(E_MAIL_VALID, false);
            countFailedValidations++;
        }
        if (!validator.validateLastName(lastName)) {
            request.setAttribute(LAST_NAME_VALID, false);
            countFailedValidations++;
        }
        if (!validator.validateFirstName(firstName)) {
            request.setAttribute(FIRST_NAME_VALID, false);
            countFailedValidations++;
        }
        if (!validator.validateMiddleName(middleName)) {
            request.setAttribute(LAST_NAME_VALID, false);
            countFailedValidations++;
        }
        if (!validator.validatePhone(phone)) {
            request.setAttribute(PHONE_VALID, false);
            countFailedValidations++;
        }
        if (!validator.validatePassport(passport)) {
            request.setAttribute(PASSPORT_VALID, false);
            countFailedValidations++;
        }
        if (!validator.validateAddress(address)) {
            request.setAttribute(ADDRESS_VALID, false);
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
                request.setAttribute(UNIQUE_LOGIN, uniqueLogin);
                request.setAttribute(UNIQUE_EMAIL, uniqueEmail);
                request.setAttribute(UNIQUE_PASSPORT, uniquePassport);
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
