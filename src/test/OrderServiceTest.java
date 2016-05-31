import by.epam.carrental.dao.connectionpoolhelper.exception.ConnectionPoolException;
import by.epam.carrental.dao.impl.ConnectionPooldb;
import by.epam.carrental.service.OrderService;
import by.epam.carrental.service.exception.ServiceException;
import org.junit.*;

public class OrderServiceTest {

    @Before
    public void initConnPool() throws ConnectionPoolException {
        ConnectionPooldb pool = ConnectionPooldb.getInstance();
        pool.initPoolData();
    }

    @After
    public void disposeConnPool() throws ConnectionPoolException {
        ConnectionPooldb pool = ConnectionPooldb.getInstance();
        pool.dispose();
    }

    @Test
    public void addOrderTest() throws ServiceException{

        OrderService orderService = OrderService.getInstance();

        int userID = 2;
        int carID = 23;
        String dateFrom = "2016-04-05 00:00";
        String dateTo = "2016-04-07 00:00";
        String shippingPlace = "Вокзал";
        String returnPlace = "Вокзал";

        boolean orderAdded = orderService.addOrder(userID, carID, dateFrom, dateTo, shippingPlace, returnPlace);
        Assert.assertTrue(orderAdded);
    }

    @Test (expected = ServiceException.class)
    public void addOrderTest2() throws ServiceException{

        OrderService orderService = OrderService.getInstance();

        int userID = 7;
        int carID = 5;
        String dateFrom = "2016-04-05";
        String dateTo = "2016-04-05";
        String shippingPlace = "Вокзал";
        String returnPlace = "Вокзал";

        boolean orderAdded = orderService.addOrder(userID, carID, dateFrom, dateTo, shippingPlace, returnPlace);
        Assert.assertFalse(orderAdded);
    }

    @Test (expected = ServiceException.class)
    public void updateStatusByIdTest() throws ServiceException{

        OrderService orderService = OrderService.getInstance();

        String status = "отклон";
        int orderID = 58;

        orderService.updateStatusById(status, orderID);
    }

    @Test
    public void updateStatusByIdTest2() throws ServiceException{

        OrderService orderService = OrderService.getInstance();

        String status = "отклонен";
        int orderID = 58;

        orderService.updateStatusById(status, orderID);
    }
}
