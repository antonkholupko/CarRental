package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.entity.Car;
import by.epam.carrental.entity.CarType;
import by.epam.carrental.service.Validator;
import by.epam.carrental.service.ValidatorUniqueCar;
import by.epam.carrental.service.CarService;
import by.epam.carrental.service.exception.ServiceException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Команда добавления автомобиля
 * <p>
 *     Класс AddCarCommand - команда для добавления автомобиля в базу данных.
 * </p>
 */
public class AddCarCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(AddCarCommand.class.getName());

    private static final int AMOUNT_CARS_ON_PAGE = 9;

    private static final String EXECUTE_STARTS_MSG = "AddCarCommand : execute : starts";
    private static final String VALID_PARAMETERS_STARTS_MSG = "AddCarCommand : validParameters : starts";

    private static final String PAGE_NUMBER_PARAM = "pageNumber";
    private static final String CAR_MODEL_PARAM = "carModel";
    private static final String CAR_YEAR_PARAM = "carYear";
    private static final String TRANSMISSION_PARAM = "transmission";
    private static final String CAR_FUEL_PARAM = "carFuel";
    private static final String CAR_TYPE_PARAM = "carType";
    private static final String GOV_NUMBER_PARAM = "govNumber";
    private static final String VIN_PARAM = "vin";
    private static final String CAR_INFO_PARAM = "car-info";
    private static final String IMAGE_PARAM = "image";
    private static final String AMOUNT_PAGES_PARAM = "amountPages";
    private static final String ALL_CARS_PARAM = "allCars";
    private static final String ALL_TYPES_PARAM = "allTypes";
    private static final String CAR_SUCCESSFUL_ADDED_PARAM = "carSuccessfulAdded";
    private static final String INVALID_MODEL_PARAM = "invalidModel";
    private static final String INVALID_YEAR_PARAM = "invalidYear";
    private static final String INVALID_GOV_NUMBER_PARAM = "invalidGovNumber";
    private static final String INVALID_VIN_CODE_PARAM = "invalidVinCode";
    private static final String INVALID_NUMBER_VIN_PARAM = "invalidNumberVin";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS_MSG);
        int amountPages = 0;
        int pageNumber = 1;
        List<Car> cars = null;
        List<CarType> carTypes = null;
        if (request.getParameter(PAGE_NUMBER_PARAM) != null) {
            pageNumber = Integer.parseInt(request.getParameter(PAGE_NUMBER_PARAM));
        }
        CarService service = CarService.getInstance();
        try {
            String model = request.getParameter(CAR_MODEL_PARAM);
            String year = request.getParameter(CAR_YEAR_PARAM);
            String transmission = request.getParameter(TRANSMISSION_PARAM);
            String fuel = request.getParameter(CAR_FUEL_PARAM);
            String type = request.getParameter(CAR_TYPE_PARAM);
            String govNumber = request.getParameter(GOV_NUMBER_PARAM);
            String vin = request.getParameter(VIN_PARAM);
            String info = request.getParameter(CAR_INFO_PARAM);
            Part image = request.getPart(IMAGE_PARAM);
            InputStream inputStream = image.getInputStream();
            if (validParameters(request)) {
                Car car = new Car();
                car.setModel(model);
                car.setYear(year);
                car.setTransmission(transmission);
                car.setFuel(fuel);
                car.setType(type);
                car.setGovNumber(govNumber);
                car.setVinCode(vin);
                car.setInfo(info);
                car.setImage(Base64.encode(IOUtils.readFully(inputStream, -1, true)));
                service.insertCar(car);

                cars = service.takeAllCars(pageNumber, AMOUNT_CARS_ON_PAGE);
                amountPages = service.countPageAmountAllCars(AMOUNT_CARS_ON_PAGE);

                carTypes = service.takeCarTypes();
                request.setAttribute(AMOUNT_PAGES_PARAM, amountPages);
                request.getSession().setAttribute(ALL_CARS_PARAM, cars);
                request.getSession().setAttribute(ALL_TYPES_PARAM, carTypes);
                request.getSession().setAttribute(CAR_SUCCESSFUL_ADDED_PARAM, true);
                return PageName.ALL_CARS;
            } else {
                return PageName.ADD_CAR;
            }

        } catch (ServletException | IOException | ServiceException ex) {
            throw new CommandException(ex);
        }
    }

    /**
     * Проверяет корректны ли параметры, описывающие автомобиль, если нет,
     * то добавляет в http-запрос информацию о неправильно введенных данных
     * @param request http-запрос
     * @return true - если все параметры корректны, false - не корректны
     * @throws CommandException
     */
    private boolean validParameters (HttpServletRequest request) throws CommandException{

        LOG.debug(VALID_PARAMETERS_STARTS_MSG);

        int countFailedValidations = 0;
        Validator validator = Validator.getInstance();

        String model = request.getParameter(CAR_MODEL_PARAM);
        String year = request.getParameter(CAR_YEAR_PARAM);
        String govNumber = request.getParameter(GOV_NUMBER_PARAM);
        String vin = request.getParameter(VIN_PARAM);


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

        if (countFailedValidations > 0) {
            return false;
        } else {
            ValidatorUniqueCar validatorUniqueCar = new ValidatorUniqueCar();
            try {
                boolean unique = validatorUniqueCar.checkUnique(govNumber, vin);
                if (unique) {
                    return true;
                } else {
                    request.setAttribute(INVALID_NUMBER_VIN_PARAM, true);
                    return false;
                }
            } catch (ServiceException ex) {
                throw new CommandException(ex);
            }
        }

    }
}
