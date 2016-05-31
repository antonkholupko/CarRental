import by.epam.carrental.dao.connectionpoolhelper.exception.ConnectionPoolException;
import by.epam.carrental.dao.impl.ConnectionPooldb;
import by.epam.carrental.entity.Car;
import by.epam.carrental.service.CarService;
import by.epam.carrental.service.exception.ServiceException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public class CarServiceTest {

    @Test(expected = ServiceException.class)
    public void insertCarTest() throws ServiceException, ConnectionPoolException {

        ConnectionPooldb pool = ConnectionPooldb.getInstance();
        pool.initPoolData();
        CarService carService = CarService.getInstance();

        Car car = new Car();
        car.setMark("mark");
        car.setImage("dss");

        carService.insertCar(car);
    }

}
