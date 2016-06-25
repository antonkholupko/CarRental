package by.epam.carrental.command.util;

import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.service.Validator;
import by.epam.carrental.service.ValidatorUniqueCar;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public final class ValidationHelper {

    private static final String VALID_PARAMETERS_STARTS_MSG = "AddCarCommand : validParameters : starts";
    private static final String VALID_PARAMETERS_ENDS_MSG = "AddCarCommand : validParameters : ends";

    private static final String INVALID_MODEL_PARAM = "invalidModel";
    private static final String INVALID_YEAR_PARAM = "invalidYear";
    private static final String INVALID_GOV_NUMBER_PARAM = "invalidGovNumber";
    private static final String INVALID_VIN_CODE_PARAM = "invalidVinCode";
    private static final String INVALID_NUMBER_VIN_PARAM = "invalidNumberVin";
    private static final String INVALID_CAR_INFO_PARAM = "invalidCarInfo";
    private static final String CAR_MODEL_PARAM = "carModel";
    private static final String CAR_YEAR_PARAM = "carYear";
    private static final String GOV_NUMBER_PARAM = "govNumber";
    private static final String VIN_PARAM = "vin";
    private static final String CAR_INFO_PARAM = "car-info";

    private static final Logger LOG = LogManager.getLogger(ValidationHelper.class.getName());

    /**
     * Проверяет корректны ли параметры, описывающие автомобиль, если нет,
     * то добавляет в http-запрос информацию о неправильно введенных данных
     *
     * @param request http-запрос
     * @return true - если все параметры корректны, false - не корректны
     * @throws by.epam.carrental.command.exception.CommandException
     */
    public static boolean validParameters(HttpServletRequest request) throws CommandException {

        LOG.debug(VALID_PARAMETERS_STARTS_MSG);

        int countFailedValidations = 0;
        Validator validator = Validator.getInstance();

        String model = request.getParameter(CAR_MODEL_PARAM);
        String year = request.getParameter(CAR_YEAR_PARAM);
        String govNumber = request.getParameter(GOV_NUMBER_PARAM);
        String vin = request.getParameter(VIN_PARAM);
        String info = request.getParameter(CAR_INFO_PARAM);

        if (!validator.validateModel(model)) {
            request.setAttribute(INVALID_MODEL_PARAM, true);
            countFailedValidations++;
        }

        if (!validator.validateCarYear(year)) {
            request.setAttribute(INVALID_YEAR_PARAM, true);
            countFailedValidations++;
        }

        if (!validator.validateGovNumber(govNumber)) {
            request.setAttribute(INVALID_GOV_NUMBER_PARAM, true);
            countFailedValidations++;
        }

        if (!validator.validateVinCode(vin)) {
            request.setAttribute(INVALID_VIN_CODE_PARAM, true);
            countFailedValidations++;
        }

        if (!validator.validateCarInfo(info)) {
            request.setAttribute(INVALID_CAR_INFO_PARAM, true);
            countFailedValidations++;
        }

        if (countFailedValidations > 0) {
            LOG.debug(VALID_PARAMETERS_ENDS_MSG);
            return false;
        } else {
            ValidatorUniqueCar validatorUniqueCar = new ValidatorUniqueCar();
            try {
                boolean unique = validatorUniqueCar.checkUnique(govNumber, vin);
                if (unique) {
                    LOG.debug(VALID_PARAMETERS_ENDS_MSG);
                    return true;
                } else {
                    request.setAttribute(INVALID_NUMBER_VIN_PARAM, true);
                    LOG.debug(VALID_PARAMETERS_ENDS_MSG);
                    return false;
                }
            } catch (ServiceException ex) {
                throw new CommandException(ex);
            }
        }

    }
}

