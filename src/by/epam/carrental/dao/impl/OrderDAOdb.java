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
            "(NOT (orders.status='canceled' OR orders.status='rejected' OR orders.status='closed' OR orders.status='returned')) AND " +
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
            "WHERE orders.userID=? ORDER BY orders.orderID DESC LIMIT ?,?;";
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
            "INNER JOIN models ON models.model = cars.model ORDER BY orders.orderID DESC LIMIT ?,?;";
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
    private static final String FIND_ORDER_STATUS_BY_CAR_ID_QUERY = "SELECT status FROM orders WHERE carID=?;";
    private static final String COUNT_ALL_ORDERS = "SELECT COUNT(orderID) FROM orders;";
    private static final String COUNT_USER_ORDERS_QUERY = "SELECT COUNT(orderID) FROM orders WHERE userID=?";

    private static final String UPDATE_STATUS_BY_ORDER_ID = "UPDATE orders SET status=? WHERE orderID=?;";
    private static final String UPDATE_STATUS_INFO_BY_ORDER_ID = "UPDATE orders SET status=?, info=? WHERE orderID=?;";
    private static final String UPDATE_DAMAGE_PRICE_BY_ORDER_ID = "UPDATE orders SET damagePrice=? WHERE orderID=?;";
    private static final String UPDATE_REAL_TIME_FROM_QUERY = "UPDATE orders SET realFromDate=? WHERE orderID=?;";
    private static final String UPDATE_REAL_TIME_TO_QUERY = "UPDATE orders SET realToDate=? WHERE orderID=?;";

    private static final String STATUS_NEW = "new";

    private static final String ADD_ORDER_STARTS_MSG = "OrderDAOdb : addOrder : starts";
    private static final String ADD_ORDER_ENDS_MSG = "OrderDAOdb : addOrder : ends";
    private static final String TAKE_ORDER_BY_ORDER_STARTS_MSG = "OrderDAOdb : takeOrderByOrder : starts";
    private static final String TAKE_ORDER_BY_ORDER_ENDS_MSG = "OrderDAOdb : takeOrderByOrder : ends";
    private static final String FIND_ORDERS_BY_USER_ID_STARTS_MSG = "OrderDAOdb : findOrdersByUserId : starts";
    private static final String FIND_ORDERS_BY_USER_ID_ENDS_MSG = "OrderDAOdb : findOrdersByUserId : ends";
    private static final String FIND_ORDER_BY_ID_STARTS_MSG = "OrderDAOdb : findOrderByOrderId : starts";
    private static final String FIND_ORDER_BY_ID_ENDS_MSG = "OrderDAOdb : findOrderByOrderId : ends";
    private static final String TAKE_ALL_ORDERS_STARTS_MSG = "OrderDAOdb : takeAllOrders : starts";
    private static final String TAKE_ALL_ORDERS_ENDS_MSG = "OrderDAOdb : takeAllOrders : ends";
    private static final String TAKE_ADMIN_ORDER_BY_ORDER_ID_STARTS_MSG = "OrderDAOdb : takeAdminOrderByOrderId : starts";
    private static final String TAKE_ADMIN_ORDER_BY_ORDER_ID_ENDS_MSG = "OrderDAOdb : takeAdminOrderByOrderId  : ends";
    private static final String UPDATE_STATUS_INFO_STARTS_MSG = "OrderDAOdb : updateStatusInfo : starts";
    private static final String UPDATE_STATUS_INFO_ENDS_MSG = "OrderDAOdb : updateStatusInfo : ends";
    private static final String UPDATE_DAMAGE_PRICE_STARTS_MSG = "OrderDAOdb : updateDamagePriceByOrderId : starts";
    private static final String UPDATE_DAMAGE_PRICE_ENDS_MSG = "OrderDAOdb : updateDamagePriceByOrderId : ends";
    private static final String UPDATE_REAL_TIME_FROM_STARTS_MSG = "OrderDAOdb : updateRealTimeFrom : starts";
    private static final String UPDATE_REAL_TIME_FROM_ENDS_MSG = "OrderDAOdb : updateRealTimeFrom : ends";
    private static final String UPDATE_REAL_TIME_TO_STARTS_MSG = "OrderDAOdb : updateRealTimeTo : starts";
    private static final String UPDATE_REAL_TIME_TO_ENDS_MSG = "OrderDAOdb : updateRealTimeTo : ends";
    private static final String COUNT_USER_ORDERS_STARTS_MSG = "OrderDAOdb : countUserOrders : starts";
    private static final String COUNT_USER_ORDERS_ENDS_MSG = "OrderDAOdb : countUserOrders : ends";
    private static final String COUNT_ALL_ORDERS_STARTS_MSG = "OrderDAOdb : countAllOrders : stats";
    private static final String COUNT_ALL_ORDERS_ENDS_MSG = "OrderDAOdb : countAllOrders : ends";
    private static final String FIND_STATUS_BY_CAR_ID_STARTS_MSG = "OrderDAOdb : findStatusByCarId : starts";
    private static final String FIND_STATUS_BY_CAR_ID_ENDS_MSG = "OrderDAOdb : findStatusByCarId : ends";

    private static final String ADD_ORDER_ERROR_MSG = "OrderDAOdb : addOrder : ERROR";
    private static final String ADD_ORDER_CLOSE_CON_ERROR_MSG = "OrderDAOdb : addOrder : close connection error";
    private static final String TAKE_ORDER_BY_ORDER_ERROR_MSG = "OrderDAOdb : takeOrderByOrder : ERROR";
    private static final String TAKE_ORDER_BY_ORDER_CLOSE_CON_ERROR_MSG = "OrderDAOdb : takeOrderByOrder : close connection error";
    private static final String FIND_ORDERS_BY_USER_ID_ERROR_MSG = "OrderDAOdb : findOrdersByUserId : ERROR";
    private static final String FIND_ORDERS_BY_USER_ID_CLOSE_CON_ERROR_MSG = "OrderDAOdb : findOrdersByUserId : close connection error";
    private static final String FIND_ORDER_BY_ID_ERROR_MSG = "OrderDAOdb : findOrderByOrderId : ERROR";
    private static final String FIND_ORDER_BY_ID_CLOE_CON_ERROR_MSG = "OrderDAOdb : findOrderByOrderId : close connection error";
    private static final String TAKE_ALL_ORDERS_ERROR_MSG = "OrderDAOdb : takeAllOrders : ERROR";
    private static final String TAKE_ALL_ORDERS_CLOSE_CON_ERROR_MSG = "OrderDAOdb : takeAllOrders : close connection error";
    private static final String TAKE_ADMIN_ORDER_BY_ORDER_ID_ERROR_MSG = "OrderDAOdb : takeAdminOrderByOrderId : ERROR";
    private static final String TAKE_ADMIN_ORDER_BY_ORDER_ID_CLOSE_CON_ERROR_MSG = "OrderDAOdb : takeAdminOrderByOrderId  : close connection error";
    private static final String UPDATE_STATUS_INFO_ERROR_MSG = "OrderDAOdb : updateStatusInfo : ERROR";
    private static final String UPDATE_STATUS_INFO_CLOSE_CON_ERROR_MSG = "OrderDAOdb : updateStatusInfo : close connection error";
    private static final String UPDATE_DAMAGE_PRICE_ERROR_MSG = "OrderDAOdb : updateDamagePriceByOrderId : ERROR";
    private static final String UPDATE_DAMAGE_PRICE_CLOSE_CON_ERROR_MSG = "OrderDAOdb : updateDamagePriceByOrderId : close connection error";
    private static final String UPDATE_REAL_TIME_FROM_ERROR_MSG = "OrderDAOdb : updateRealTimeFrom : ERROR";
    private static final String UPDATE_REAL_TIME_FROM_CLOSE_CON_ERROR_MSG = "OrderDAOdb : updateRealTimeFrom : close connection error";
    private static final String UPDATE_REAL_TIME_TO_ERROR_MSG = "OrderDAOdb : updateRealTimeTo : ERROR";
    private static final String UPDATE_REAL_TIME_TO_CLOSE_CON_ERROR_MSG = "OrderDAOdb : updateRealTimeTo : close connection error";
    private static final String COUNT_USER_ORDERS_ERROR_MSG = "OrderDAOdb : countUserOrders : ERROR";
    private static final String COUNT_USER_ORDERS_CLOSE_CON_ERROR_MSG = "OrderDAOdb : countUserOrders : close connection error";
    private static final String COUNT_ALL_ORDERS_ERROR_MSG = "OrderDAOdb : countAllOrders : ERROR";
    private static final String COUNT_ALL_ORDERS_CLOSE_CON_ERROR_MSG = "OrderDAOdb : countAllOrders : close connection error";
    private static final String FIND_STATUS_BY_CAR_ID_ERROR_MSG = "OrderDAOdb : findStatusByCarId : ERROR";
    private static final String FIND_STATUS_BY_CAR_ID_CLOSE_CON_ERROR = "orderDAOdb : findStatusByCarId : close connection error";


    @Override
    public void addOrder(Order order) throws DAOException {
        LOG.debug(ADD_ORDER_STARTS_MSG);
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
            throw new DAOException(ADD_ORDER_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps);
                }
                LOG.debug(ADD_ORDER_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(ADD_ORDER_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    public List<Integer> takeOrderByOrder(Order order) throws DAOException {
        LOG.debug(TAKE_ORDER_BY_ORDER_STARTS_MSG);
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
            throw new DAOException(TAKE_ORDER_BY_ORDER_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(TAKE_ORDER_BY_ORDER_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(TAKE_ORDER_BY_ORDER_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    public List<Order> findOrdersByUserId(int userId, int toStartPage, int ordersOnPage) throws DAOException {
        LOG.debug(FIND_ORDERS_BY_USER_ID_STARTS_MSG);
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
            ps.setInt(2, toStartPage);
            ps.setInt(3, ordersOnPage);
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
            throw new DAOException(FIND_ORDERS_BY_USER_ID_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(FIND_ORDERS_BY_USER_ID_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(FIND_ORDERS_BY_USER_ID_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    public Order findOrderByOrderId(int orderId) throws DAOException {
        LOG.debug(FIND_ORDER_BY_ID_STARTS_MSG);
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
            throw new DAOException(FIND_ORDER_BY_ID_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(FIND_ORDER_BY_ID_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(FIND_ORDER_BY_ID_CLOE_CON_ERROR_MSG, ex);
            }
        }
    }

    public List<Order> takeAllOrders(int toStartPage, int ordersOnPage) throws DAOException {
        LOG.debug(TAKE_ALL_ORDERS_STARTS_MSG);
        List<Order> orders = new ArrayList<>();
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            PreparedStatement ps = connection.prepareStatement(TAKE_ALL_ORDERS_QUERY);
            ps.setInt(1, toStartPage);
            ps.setInt(2, ordersOnPage);
            rs = ps.executeQuery();
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
            throw new DAOException(TAKE_ALL_ORDERS_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, st, rs);
                }
                LOG.debug(TAKE_ALL_ORDERS_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(TAKE_ALL_ORDERS_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    public Order takeAdminOrderByOrderId(int orderId) throws DAOException {
        LOG.debug(TAKE_ADMIN_ORDER_BY_ORDER_ID_STARTS_MSG);
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
            throw new DAOException(TAKE_ADMIN_ORDER_BY_ORDER_ID_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(TAKE_ADMIN_ORDER_BY_ORDER_ID_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(TAKE_ADMIN_ORDER_BY_ORDER_ID_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    public void updateStatusById(String status, int orderId) throws DAOException{
        LOG.debug(UPDATE_STATUS_INFO_STARTS_MSG);
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
            throw new DAOException(UPDATE_STATUS_INFO_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps);
                }
                LOG.debug(UPDATE_STATUS_INFO_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(UPDATE_STATUS_INFO_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    public void updateStatusById(String status, int orderId, String orderInfo) throws DAOException{
        LOG.debug(UPDATE_STATUS_INFO_STARTS_MSG);
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
            throw new DAOException(UPDATE_STATUS_INFO_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps);
                }
                LOG.debug(UPDATE_STATUS_INFO_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(UPDATE_STATUS_INFO_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    public void updateDamagePriceByOrderId(int orderId, double damagePrice) throws DAOException{
        LOG.debug(UPDATE_DAMAGE_PRICE_STARTS_MSG);
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
            throw new DAOException(UPDATE_DAMAGE_PRICE_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps);
                }
                LOG.debug(UPDATE_DAMAGE_PRICE_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(UPDATE_DAMAGE_PRICE_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    public void updateRealTimeFrom(int orderId, String date) throws DAOException{
        LOG.debug(UPDATE_REAL_TIME_FROM_STARTS_MSG);
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
            throw new DAOException(UPDATE_REAL_TIME_FROM_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps);
                }
                LOG.debug(UPDATE_REAL_TIME_FROM_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(UPDATE_REAL_TIME_FROM_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    public void updateRealTimeTo(int orderId, String date) throws DAOException{
        LOG.debug(UPDATE_REAL_TIME_TO_STARTS_MSG);
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
            throw new DAOException(UPDATE_REAL_TIME_TO_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps);
                }
                LOG.debug(UPDATE_REAL_TIME_TO_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(UPDATE_REAL_TIME_TO_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    public int countUserOrders(int userId) throws DAOException {
        LOG.debug(COUNT_USER_ORDERS_STARTS_MSG);
        int amountOrders = 0;
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(COUNT_USER_ORDERS_QUERY);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                amountOrders = rs.getInt(1);
            }
            return amountOrders;
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DAOException(COUNT_USER_ORDERS_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(COUNT_USER_ORDERS_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(COUNT_USER_ORDERS_CLOSE_CON_ERROR_MSG, ex);
            }
        }

    }

    public int countAllOrders() throws DAOException {
        LOG.debug(COUNT_ALL_ORDERS_STARTS_MSG);
        int ordersAmount = 0;
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            st = connection.createStatement();
            rs = st.executeQuery(COUNT_ALL_ORDERS);
            while (rs.next()) {
                ordersAmount = rs.getInt(1);
            }
            return ordersAmount;
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DAOException(COUNT_ALL_ORDERS_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, st, rs);
                }
                LOG.debug(COUNT_ALL_ORDERS_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(COUNT_ALL_ORDERS_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    @Override
    public List<String> findStatusByCarId(int carID) throws DAOException {
        LOG.debug(FIND_STATUS_BY_CAR_ID_STARTS_MSG);
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(FIND_ORDER_STATUS_BY_CAR_ID_QUERY);
            ps.setInt(1, carID);
            rs = ps.executeQuery();
            List<String> orderStatuses = new ArrayList<>();
            while (rs.next()) {
                orderStatuses.add(rs.getString(1));
            }
            LOG.debug(FIND_STATUS_BY_CAR_ID_ENDS_MSG);
            return orderStatuses;
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DAOException(FIND_STATUS_BY_CAR_ID_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(COUNT_ALL_ORDERS_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(FIND_STATUS_BY_CAR_ID_CLOSE_CON_ERROR, ex);
            }
        }
    }
}
