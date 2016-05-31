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

    private static  final int AMOUNT_CARS_ON_PAGE = 9;

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug("AddCarCommand : execute");
        int amountPages = 0;
        int pageNumber = 1;
        List<Car> cars = null;
        List<CarType> carTypes = null;
        if (request.getParameter("pageNumber") != null) {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
        }
        CarService service = CarService.getInstance();
        try {
            String model = request.getParameter("carModel");
            String year = request.getParameter("carYear");
            String transmission = request.getParameter("transmission");
            String fuel = request.getParameter("carFuel");
            String type = request.getParameter("carType");
            String govNumber = request.getParameter("govNumber");
            String vin = request.getParameter("vin");
            String info = request.getParameter("car-info");
            //String filePath = request.getParameter("image");
            Part image = request.getPart("image");
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
                request.setAttribute("amountPages", amountPages);
                request.getSession().setAttribute("allCars", cars);
                request.getSession().setAttribute("allTypes", carTypes);
                request.getSession().setAttribute("carSuccessfulAdded", true);
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

        int countFailedValidations = 0;
        Validator validator = Validator.getInstance();

        String model = request.getParameter("carModel");
        String year = request.getParameter("carYear");
        String govNumber = request.getParameter("govNumber");
        String vin = request.getParameter("vin");


        if (!validator.validateModel(model)) {
            request.setAttribute("invalidModel", true);
            countFailedValidations++;
        }

        if (!validator.validateCarYear(year)) {
            request.setAttribute("invalidYear", true);
            countFailedValidations++;
        }

        if (!validator.validateGovNumber(govNumber)) {
            request.setAttribute("invalidGovNumber", true);
            countFailedValidations++;
        }

        if (!validator.validateVinCode(vin)) {
            request.setAttribute("invalidVinCode", true);
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
                    request.setAttribute("invalidNumberVin", true);
                    return false;
                }
            } catch (ServiceException ex) {
                throw new CommandException(ex);
            }
        }

    }
}
