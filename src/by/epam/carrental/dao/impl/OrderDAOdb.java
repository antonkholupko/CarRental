package by.epam.carrental.dao.impl;

import by.epam.carrental.dao.OrderDAO;
import by.epam.carrental.dao.connectionpoolhelper.exception.ConnectionPoolException;
import by.epam.carrental.dao.exception.DAOException;
import by.epam.carrental.entity.Car;
import by.epam.carrental.entity.Order;
import by.epam.carrental.entity.User;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для вставки/обновления/получения сущностей класса Order в/из базу(ы) данных
 */
public class OrderDAOdb implements OrderDAO {

    private static final Logger LOG = LogManager.getLogger(OrderDAOdb.class.getName());
    private static final String ADD_ORDER_QUERY = "INSERT INTO orders (userID, carID, supposedFromDate, supposedToDate, " +
            "shippingPlace, returnPlace, status) VALUES (?,?,?,?,?,?,?);";
    private static final String CHECK_INSERT_QUERY = "SELECT carID FROM orders WHERE " +
            "(NOT (orders.status='отменен' OR orders.status='отклонен' OR orders.status='закрыт' OR orders.status='возвращен')) AND " +
            "((? BETWEEN orders.supposedFromDate AND orders.supposedToDate) OR " +
            "(? BETWEEN orders.supposedFromDate AND orders.supposedToDate) OR " +
            "(? BETWEEN orders.supposedFromDate AND orders.supposedToDate) OR " +
            "((? BETWEEN orders.supposedFromDate AND orders.supposedToDate) " +
            "AND (? BETWEEN orders.supposedFromDate AND orders.supposedToDate)) OR " +
            "((orders.supposedFromDate BETWEEN ? AND ?)AND(orders.supposedToDate " +
            "BETWEEN ? AND ?)));";
    private static final String FIND_BY_USER_ID_QUERY = "SELECT models.mark, cars.model, cars.govNumber, " +
            "orders.orderID, orders.supposedFromDate, orders.supposedToDate, orders.realFromDate, orders.realToDate, " +
            "orders.shippingPlace, orders.returnPlace, orders.orderPrice, orders.damagePrice, orders.status " +
            "FROM orders INNER JOIN cars ON cars.carID = orders.carID " +
            "INNER JOIN models ON models.model = cars.model " +
            "WHERE orders.userID=? ORDER BY orders.orderID DESC;";
    private static final String FIND_BY_ORDER_ID_QUERY = "SELECT models.mark, cars.model, cars.year,  cars.type, " +
            "cars.fuel, cars.transmission, cars.govNumber, cars.info, cars.image, " +
            "orders.orderID, orders.supposedFromDate, orders.supposedToDate, orders.realFromDate, " +
            "orders.realToDate, orders.status, " +
            "orders.shippingPlace, orders.returnPlace, orders.orderPrice, orders.damagePrice, orders.info FROM orders " +
            "INNER JOIN cars ON cars.carID = orders.carID " +
            "INNER JOIN models ON cars.model = models.model " +
            "WHERE orders.orderID=?;";
    private static final String TAKE_ALL_ORDERS_QUERY = "SELECT orders.orderID, users.lastName, users.firstName, " +
            "users.middleName, models.mark, cars.model, cars.govNumber, orders.status, orders.orderPrice, orders.damagePrice " +
            "FROM orders INNER JOIN cars ON orders.carID = cars.carID " +
            "INNER JOIN users ON orders.userID = users.userID " +
            "INNER JOIN models ON models.model = cars.model ORDER BY orders.orderID DESC;";
    private static final String TAKE_ADMIN_ORDER_BY_ORDER_ID_QUERY = "SELECT orders.orderID, users.login, " +
            "users.lastName, users.firstName, users.middleName, users.email, users.phone, " +
            "users.passport, users.address, models.mark, cars.model, cars.govNumber, cars.year, " +
            "cars.transmission, cars.type, cars.status, cars.fuel, cars.image, " +
            "orders.supposedFromDate, orders.supposedToDate, orders.realFromDate, orders.realToDate, orders.status, " +
            "orders.shippingPlace, orders.returnPlace, orders.orderPrice, orders.damagePrice, orders.info " +
            "FROM orders INNER JOIN cars ON orders.carID = cars.carID " +
            "INNER JOIN users ON orders.userID = users.userID " +
            "INNER JOIN models ON models.model = cars.model " +
            "WHERE orders.orderID=?;";
    private static final String UPDATE_STATUS_BY_ORDER_ID = "UPDATE orders SET status=? WHERE orderID=?;";
    private static final String UPDATE_STATUS_INFO_BY_ORDER_ID = "UPDATE orders SET status=?, info=? WHERE orderID=?;";
    private static final String UPDATE_REAL_DATE_BY_ORDER_ID = "UPDATE orders SET realFromDate=?, realToDate=? " +
            "WHERE orderID=?;";
    private static final String UPDATE_DAMAGE_PRICE_BY_ORDER_ID = "UPDATE orders SET damagePrice=? WHERE orderID=?;";
    private static final String UPDATE_REAL_TIME_FROM_QUERY = "UPDATE orders SET realFromDate=? WHERE orderID=?;";
    private static final String UPDATE_REAL_TIME_TO_QUERY = "UPDATE orders SET realToDate=? WHERE orderID=?;";

    private static final String UPDATE_REAL_TIME_FROM_MSG = "OrderDAOdb : updateRealTimeFrom";
    private static final String UPDATE_REAL_TIME_TO_MSG = "OrderDAOdb : updateRealTimeTo";
    private static final String UPDATE_DAMAGE_PRICE_BY_ID_MSG = "OrderDAOdb : updateDamagePriceByOrderId";
    private static final String UPDATE_REAL_DATE_MSG = "OrderDAOdb : updateRealDateByOrderId";
    private static final String UPDATE_STATUS_MSG = "OrderDAOdb : updateStatusById";
    private static final String TAKE_ADMIN_ORDER_MSG = "OrderDAOdb : takeAdminOrderByOrderId";
    private static final String ADD_ORDER_MSG = "OrderDAOdb : addOrder";
    private static final String STATUS_NEW = "новый";
    private static final String TAKE_ORDER = "OrderDAOdb : takeOrderByOrderId";
    private static final String FIND_ORDERS = "OrderDAOdb : findOrdersByUserId";
    private static final String FIND_ORDER = "OrderDAOdb : findOrderByOrderId";
    private static final String TAKE_ALL_ORDERS = "OrderDAOdb : takeAllOrders";


    @Override
    public void addOrder(Order order) throws DAOException {
        LOG.debug(ADD_ORDER_MSG);
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(ADD_ORDER_QUERY);
            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getCarId());
            ps.setString(3, order.getSupposedDateFrom());
            ps.setString(4, order.getSupposedDateTo());
            ps.setString(5, order.getShippingPlace());
            ps.setString(6, order.getReturnPlace());
            ps.setString(7, STATUS_NEW);
            ps.executeUpdate();
        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException(ex);
        } finally {
            try {
                connectionPooldb.closeConnection(connection, ps);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(ex);
            }
        }
    }

    public List<Integer> takeOrderByOrder(Order order) throws DAOException {
        LOG.debug(TAKE_ORDER);
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int carId = 0;
        List<Integer> carIds = new ArrayList<>();
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(CHECK_INSERT_QUERY);
            ps.setString(1, order.getSupposedDateFrom());
            ps.setString(2, order.getSupposedDateTo());
            ps.setString(3, order.getSupposedDateTo());
            ps.setString(4, order.getSupposedDateFrom());
            ps.setString(5, order.getSupposedDateTo());
            ps.setString(6, order.getSupposedDateFrom());
            ps.setString(7, order.getSupposedDateTo());
            ps.setString(8, order.getSupposedDateFrom());
            ps.setString(9, order.getSupposedDateTo());
            rs = ps.executeQuery();
            while (rs.next()) {
                carIds.add(rs.getInt(1));
            }
            return carIds;
        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException(ex);
        } finally {
            try {
                connectionPooldb.closeConnection(connection, ps, rs);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(ex);
            }
        }
    }

    public List<Order> findOrdersByUserId(int userId) throws DAOException{
        LOG.debug(FIND_ORDERS);
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Order> orders = new ArrayList<>();
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(FIND_BY_USER_ID_QUERY);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                Car car = new Car();
                car.setMark(rs.getString(1));
                car.setModel(rs.getString(2));
                car.setGovNumber(rs.getString(3));
                order.setId(rs.getInt(4));
                order.setSupposedDateFrom(rs.getString(5));
                order.setSupposedDateTo(rs.getString(6));
                order.setRealDateFrom(rs.getString(7));
                order.setRealDateTo(rs.getString(8));
                order.setShippingPlace(rs.getString(9));
                order.setReturnPlace(rs.getString(10));
                order.setOrderPrice(rs.getDouble(11));
                order.setDamagePrice(rs.getDouble(12));
                order.setStatus(rs.getString(13));
                order.setCar(car);
                orders.add(order);
            }
            return orders;
        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException(ex);
        } finally {
            try {
                connectionPooldb.closeConnection(connection, ps, rs);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(ex);
            }
        }
    }

    public Order findOrderByOrderId(int orderId) throws DAOException {
        LOG.debug(FIND_ORDER);
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(FIND_BY_ORDER_ID_QUERY);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            Order order = new Order();
            if (rs.next()) {
                Car car = new Car();
                car.setMark(rs.getString(1));
                car.setModel(rs.getString(2));
                car.setYear(rs.getString(3));
                car.setType(rs.getString(4));
                car.setFuel(rs.getString(5));
                car.setTransmission(rs.getString(6));
                car.setGovNumber(rs.getString(7));
                car.setInfo(rs.getString(8));
                car.setImage(Base64.encode(rs.getBytes(9)));
                order.setId(rs.getInt(10));
                order.setSupposedDateFrom(rs.getString(11));
                order.setSupposedDateTo(rs.getString(12));
                order.setRealDateFrom(rs.getString(13));
                order.setRealDateTo(rs.getString(14));
                order.setStatus(rs.getString(15));
                order.setShippingPlace(rs.getString(16));
                order.setReturnPlace(rs.getString(17));
                order.setOrderPrice(rs.getDouble(18));
                order.setDamagePrice(rs.getDouble(19));
                order.setInfo(rs.getString(20));
                order.setCar(car);
            }
            return order;
        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException(ex);
        } finally {
            try {
                connectionPooldb.closeConnection(connection, ps, rs);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(ex);
            }
        }
    }

    public List<Order> takeAllOrders() throws DAOException {
        LOG.debug(TAKE_ALL_ORDERS);
        List<Order> orders = new ArrayList<>();
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            st = connection.createStatement();
            rs = st.executeQuery(TAKE_ALL_ORDERS_QUERY);
            while (rs.next()) {
                Order order = new Order();
                Car car = new Car();
                User user = new User();
                order.setId(rs.getInt(1));
                user.setLastName(rs.getString(2));
                user.setFirstName(rs.getString(3));
                user.setMiddleName(rs.getString(4));
                car.setMark(rs.getString(5));
                car.setModel(rs.getString(6));
                car.setGovNumber(rs.getString(7));
                order.setStatus(rs.getString(8));
                order.setOrderPrice(rs.getDouble(9));
                order.setDamagePrice(rs.getDouble(10));
                order.setCar(car);
                order.setUser(user);
                orders.add(order);
            }
            return orders;
        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException(ex);
        } finally {
            try {
                connectionPooldb.closeConnection(connection, st, rs);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(ex);
            }
        }
    }

    public Order takeAdminOrderByOrderId(int orderId) throws DAOException {
        LOG.debug(TAKE_ADMIN_ORDER_MSG);
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(TAKE_ADMIN_ORDER_BY_ORDER_ID_QUERY);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();
            Order order = new Order();
            Car car = new Car();
            User user = new User();
            if (rs.next()) {
                order.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setFirstName(rs.getString(4));
                user.setMiddleName(rs.getString(5));
                user.setEMail(rs.getString(6));
                user.setPhone(rs.getString(7));
                user.setPassport(rs.getString(8));
                user.setAddress(rs.getString(9));
                car.setMark(rs.getString(10));
                car.setModel(rs.getString(11));
                car.setGovNumber(rs.getString(12));
                car.setYear(rs.getString(13));
                car.setTransmission(rs.getString(14));
                car.setType(rs.getString(15));
                car.setStatus(rs.getString(16));
                car.setFuel(rs.getString(17));
                car.setImage(Base64.encode(rs.getBytes(18)));
                order.setSupposedDateFrom(rs.getString(19));
                order.setSupposedDateTo(rs.getString(20));
                order.setRealDateFrom(rs.getString(21));
                order.setRealDateTo(rs.getString(22));
                order.setStatus(rs.getString(23));
                order.setShippingPlace(rs.getString(24));
                order.setReturnPlace(rs.getString(25));
                order.setOrderPrice(rs.getDouble(26));
                order.setDamagePrice(rs.getDouble(27));
                order.setInfo(rs.getString(28));
                order.setCar(car);
                order.setUser(user);
            }
            return order;
        } catch(ConnectionPoolException | SQLException ex) {
            throw new DAOException(ex);
        } finally {
            try {
                connectionPooldb.closeConnection(connection, ps, rs);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(ex);
            }
        }
    }

    public void updateStatusById(String status, int orderId) throws DAOException{
        LOG.debug(UPDATE_STATUS_MSG);
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(UPDATE_STATUS_BY_ORDER_ID);
            ps.setString(1, status);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException(ex);
        } finally {
            try {
                connectionPooldb.closeConnection(connection, ps);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(ex);
            }
        }
    }

    public void updateStatusById(String status, int orderId, String orderInfo) throws DAOException{
        LOG.debug(UPDATE_STATUS_MSG);
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(UPDATE_STATUS_INFO_BY_ORDER_ID);
            ps.setString(1, status);
            ps.setString(2, orderInfo);
            ps.setInt(3, orderId);
            ps.executeUpdate();
        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException(ex);
        } finally {
            try {
                connectionPooldb.closeConnection(connection, ps);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(ex);
            }
        }
    }

    public void updateRealDateByOrderId(String realDateFrom, String realDateTo, int orderId) throws DAOException {
        LOG.debug(UPDATE_REAL_DATE_MSG);
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(UPDATE_REAL_DATE_BY_ORDER_ID);
            ps.setString(1, realDateFrom);
            ps.setString(2, realDateTo);
            ps.setInt(3, orderId);
            ps.executeUpdate();
        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException(ex);
        } finally {
            try {
                connectionPooldb.closeConnection(connection, ps);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(ex);
            }
        }
    }

    public void updateDamagePriceByOrderId(int orderId, double damagePrice) throws DAOException{
        LOG.debug(UPDATE_DAMAGE_PRICE_BY_ID_MSG);
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(UPDATE_DAMAGE_PRICE_BY_ORDER_ID);
            ps.setDouble(1, damagePrice);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException(ex);
        } finally {
            try {
                connectionPooldb.closeConnection(connection, ps);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(ex);
            }
        }
    }

    public void updateRealTimeFrom(int orderId, String date) throws DAOException{
        LOG.debug(UPDATE_REAL_TIME_FROM_MSG);
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(UPDATE_REAL_TIME_FROM_QUERY);
            ps.setString(1, date);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException(ex);
        } finally {
            try {
                connectionPooldb.closeConnection(connection, ps);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(ex);
            }
        }
    }

    public void updateRealTimeTo(int orderId, String date) throws DAOException{
        LOG.debug(UPDATE_REAL_TIME_TO_MSG);
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(UPDATE_REAL_TIME_TO_QUERY);
            ps.setString(1, date);
            ps.setInt(2, orderId);
            ps.executeUpdate();
        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException(ex);
        } finally {
            try {
                connectionPooldb.closeConnection(connection, ps);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(ex);
            }
        }
    }
}
