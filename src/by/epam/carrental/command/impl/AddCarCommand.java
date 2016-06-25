package by.epam.carrental.command.impl;

import by.epam.carrental.command.Command;
import by.epam.carrental.command.PageName;
import by.epam.carrental.command.exception.CommandException;
import by.epam.carrental.command.util.ValidationHelper;
import by.epam.carrental.entity.Car;
import by.epam.carrental.service.CarService;
import by.epam.carrental.service.exception.ServiceException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

/**
 * Команда добавления автомобиля
 * <p>
 *     Класс AddCarCommand - команда для добавления автомобиля в базу данных.
 * </p>
 */
public class AddCarCommand implements Command {

    private static final Logger LOG = LogManager.getLogger(AddCarCommand.class.getName());

    private static final String EXECUTE_STARTS_MSG = "AddCarCommand : execute : starts";
    private static final String EXECUTE_ENDS_MSG = "AddCarCommand : execute : ends";


    private static final String CAR_MODEL_PARAM = "carModel";
    private static final String CAR_YEAR_PARAM = "carYear";
    private static final String TRANSMISSION_PARAM = "transmission";
    private static final String CAR_FUEL_PARAM = "carFuel";
    private static final String CAR_TYPE_PARAM = "carType";
    private static final String GOV_NUMBER_PARAM = "govNumber";
    private static final String VIN_PARAM = "vin";
    private static final String CAR_INFO_PARAM = "car-info";
    private static final String IMAGE_PARAM = "image";
    private static final String CAR_SUCCESSFUL_ADDED_PARAM = "carSuccessfulAdded";

    private static final String PROCESS_REQUEST_PARAM = "processRequest";

    private static final String FORWARD_VALUE = "forward";
    private static final String REDIRECT_VALUE = "redirect";

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        LOG.debug(EXECUTE_STARTS_MSG);
        CarService service = CarService.getInstance();
        try {
            Part image = request.getPart(IMAGE_PARAM);
            InputStream inputStream = image.getInputStream();
            if (ValidationHelper.validParameters(request)) {
                Car car = new Car();
                car.setModel(request.getParameter(CAR_MODEL_PARAM));
                car.setYear(request.getParameter(CAR_YEAR_PARAM));
                car.setTransmission(request.getParameter(TRANSMISSION_PARAM));
                car.setFuel(request.getParameter(CAR_FUEL_PARAM));
                car.setType(request.getParameter(CAR_TYPE_PARAM));
                car.setGovNumber(request.getParameter(GOV_NUMBER_PARAM));
                car.setVinCode(request.getParameter(VIN_PARAM));
                car.setInfo(request.getParameter(CAR_INFO_PARAM));
                car.setImage(Base64.encode(IOUtils.readFully(inputStream, -1, true)));
                service.insertCar(car);

                request.getSession().setAttribute(CAR_SUCCESSFUL_ADDED_PARAM, true);
                request.setAttribute(PROCESS_REQUEST_PARAM, REDIRECT_VALUE);
                LOG.debug(EXECUTE_ENDS_MSG);
                return PageName.ADMIN_SUCCESS;
            } else {
                request.setAttribute(PROCESS_REQUEST_PARAM, FORWARD_VALUE);
                LOG.debug(EXECUTE_ENDS_MSG);
                return PageName.ADD_CAR;
            }
        } catch (ServletException | IOException | ServiceException ex) {
            throw new CommandException(ex);
        }
    }
}
