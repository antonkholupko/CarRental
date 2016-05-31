package by.epam.carrental.dao.impl;

import by.epam.carrental.dao.CarDAO;
import by.epam.carrental.dao.connectionpoolhelper.exception.ConnectionPoolException;
import by.epam.carrental.dao.exception.DAOException;
import by.epam.carrental.entity.Car;
import by.epam.carrental.entity.CarType;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для получения сущностей класса Car из базы даных, или их вставки/обновления/удаления в бд
 */
public class CarDAOdb implements CarDAO {

    private static final Logger LOG = LogManager.getLogger(CarDAOdb.class.getName());
    private static final String TAKE_MARKS_QUERY = "SELECT * FROM marks;";
    private static final String TAKE_MODELS_QUERY = "SELECT model FROM models WHERE mark=?;";
    private static final String TAKE_CARS_BY_TYPE_QUERY = "SELECT cars.carID, models.mark, cars.model, cars.year, cars.transmission, " +
            "cars.type, cars.fuel, cars.info, cars.image, cartypes.price " +
            "FROM carrental.cars " +
            "INNER JOIN models ON models.model = cars.model " +
            "INNER JOIN cartypes ON cars.type = cartypes.type " +
            "WHERE cars.type=?;";

    private static final String TAKE_CARS_UNUSED_QUERY = "SELECT cars.carID, models.mark, cars.model, cars.year, " +
            "cars.transmission, cars.type, cars.fuel, cars.info, cars.image, cartypes.price " +
            "FROM cars INNER JOIN models ON models.model = cars.model INNER JOIN cartypes ON cars.type = cartypes.type " +
            "WHERE carID NOT IN " +
            "(SELECT carID FROM orders WHERE " +
            "(NOT (orders.status='отменен' OR orders.status='отклонен' OR orders.status='закрыт' OR orders.status='возвращен')) AND " +
            "((? BETWEEN orders.supposedFromDate AND orders.supposedToDate) OR " +
            "(? BETWEEN orders.supposedFromDate AND orders.supposedToDate) OR " +
            "(? BETWEEN orders.supposedFromDate AND orders.supposedToDate) OR" +
            "((? BETWEEN orders.supposedFromDate AND orders.supposedToDate) " +
            "AND (? BETWEEN orders.supposedFromDate AND orders.supposedToDate)) OR " +
            "((orders.supposedFromDate BETWEEN ? AND ?)AND(orders.supposedToDate " +
            "BETWEEN ? AND ?)))) GROUP BY carID DESC;";

    private static final String TAKE_CARS_UNUSED_BY_TYPE_QUERY = "SELECT cars.carID, models.mark, cars.model, cars.year, " +
            "cars.transmission, cars.type, cars.fuel, cars.info, cars.image, cartypes.price " +
            "FROM cars INNER JOIN models ON models.model = cars.model INNER JOIN cartypes ON cars.type = cartypes.type " +
            "WHERE carID NOT IN " +
            "(SELECT carID FROM orders WHERE " +
            "(NOT (orders.status='отменен' OR orders.status='отклонен' OR orders.status='закрыт' OR orders.status='возвращен')) AND " +
            "((? BETWEEN orders.supposedFromDate AND orders.supposedToDate) OR " +
            "(? BETWEEN orders.supposedFromDate AND orders.supposedToDate) OR " +
            "(? BETWEEN orders.supposedFromDate AND orders.supposedToDate) OR" +
            "((? BETWEEN orders.supposedFromDate AND orders.supposedToDate) " +
            "AND (? BETWEEN orders.supposedFromDate AND orders.supposedToDate)) OR " +
            "((orders.supposedFromDate BETWEEN ? AND ?)AND(orders.supposedToDate " +
            "BETWEEN ? AND ?)))) AND cars.type=? GROUP BY carID DESC;";
    private static final String TAKE_ALL_CARS_QUERY = "SELECT cars.carID, models.mark, cars.model, cars.year, cars.transmission, " +
            "cars.type, cars.fuel, cars.info, cars.image, cartypes.price " +
            "FROM carrental.cars " +
            "INNER JOIN models ON models.model = cars.model " +
            "INNER JOIN cartypes ON cars.type = cartypes.type GROUP BY carID DESC LIMIT ?,?;";
    private static final String TAKE_ALL_TYPES = "SELECT * FROM cartypes;";
    private static final String INSERT_CAR_QUERY = "INSERT INTO cars (vin, model, govNumber, year, transmission, type, status, " +
            "fuel, info, image) VALUES (?,?,?,?,?,?,?,?,?,?);";
    private static final String FIND_CAR_BY_GOV_NUMBER_VIN = "SELECT model FROM cars WHERE govNumber=? OR vin=?;";
    private static final String DELETE_CAR_BY_ID = "DELETE FROM cars WHERE carID=?;";
    private static final String COUNT_ALL_CARS = "SELECT COUNT(carID) FROM cars;";

    @Override
    public List<String> takeMarks() throws DAOException {
        LOG.debug("CarDAOdb : takeMarks");
        List<String> marks = new ArrayList<>();
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            st = connection.createStatement();
            rs = st.executeQuery(TAKE_MARKS_QUERY);
            while (rs.next()) {
                marks.add(rs.getString(1));
            }
            return marks;
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

    @Override
    public List<String> takeModels(String mark) throws DAOException {
        LOG.debug("CarDAOdb : takeModels");
        List<String> models = new ArrayList<>();
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(TAKE_MODELS_QUERY);
            ps.setString(1, mark);
            rs = ps.executeQuery();
            while (rs.next()) {
                models.add(rs.getString(1));
            }
            return models;
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

    @Override
    public List<CarType> takeCarTypes() throws DAOException {
        LOG.debug("CarDAOdb : takeCarTypes");
        List<CarType> carTypes = new ArrayList<>();
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            st = connection.createStatement();
            rs = st.executeQuery(TAKE_ALL_TYPES);
            while (rs.next()) {
                CarType carType = new CarType();
                carType.setType(rs.getString(1));
                carType.setPrice(rs.getDouble(2));
                carTypes.add(carType);
            }
            return carTypes;
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

    @Override
    public List<Car> takeCarsByType(String type) throws DAOException {
        LOG.debug("CarDAOdb : takeCarsByType");
        List<Car> cars = new ArrayList<>();
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(TAKE_CARS_BY_TYPE_QUERY);
            ps.setString(1, type);
            rs = ps.executeQuery();
            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt(1));
                car.setMark(rs.getString(2));
                car.setModel(rs.getString(3));
                car.setYear(rs.getString(4));
                car.setTransmission(rs.getString(5));
                car.setType(rs.getString(6));
                car.setFuel(rs.getString(7));
                car.setInfo(rs.getString(8));
                car.setImage(Base64.encode(rs.getBytes(9)));
                car.setPrice(rs.getDouble(10));
                cars.add(car);
            }
            return cars;
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

    @Override
    public List<Car> takeUnusedCars(String supposedDateFrom, String supposedDateTo) throws DAOException {
        LOG.debug("CarDAOdb : takeUnusedCars");
        List<Car> cars = new ArrayList<>();
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(TAKE_CARS_UNUSED_QUERY);
            ps.setString(1, supposedDateFrom);
            ps.setString(2, supposedDateTo);
            ps.setString(3, supposedDateTo);
            ps.setString(4, supposedDateFrom);
            ps.setString(5, supposedDateTo);
            ps.setString(6, supposedDateFrom);
            ps.setString(7, supposedDateTo);
            ps.setString(8, supposedDateFrom);
            ps.setString(9, supposedDateTo);
            rs = ps.executeQuery();
            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt(1));
                car.setMark(rs.getString(2));
                car.setModel(rs.getString(3));
                car.setYear(rs.getString(4));
                car.setTransmission(rs.getString(5));
                car.setType(rs.getString(6));
                car.setFuel(rs.getString(7));
                car.setInfo(rs.getString(8));
                car.setImage(Base64.encode(rs.getBytes(9)));
                car.setPrice(rs.getDouble(10));
                cars.add(car);
            }
            return cars;
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

    @Override
    public List<Car> takeUnusedCarsByType(String type, String supposedDateFrom, String supposedDateTo) throws DAOException {
        LOG.debug("CarDAOdb : takeUnusedCars");
        List<Car> cars = new ArrayList<>();
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(TAKE_CARS_UNUSED_BY_TYPE_QUERY);
            ps.setString(1, supposedDateFrom);
            ps.setString(2, supposedDateTo);
            ps.setString(3, supposedDateTo);
            ps.setString(4, supposedDateFrom);
            ps.setString(5, supposedDateTo);
            ps.setString(6, supposedDateFrom);
            ps.setString(7, supposedDateTo);
            ps.setString(8, supposedDateFrom);
            ps.setString(9, supposedDateTo);
            ps.setString(10, type);
            rs = ps.executeQuery();
            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt(1));
                car.setMark(rs.getString(2));
                car.setModel(rs.getString(3));
                car.setYear(rs.getString(4));
                car.setTransmission(rs.getString(5));
                car.setType(rs.getString(6));
                car.setFuel(rs.getString(7));
                car.setInfo(rs.getString(8));
                car.setImage(Base64.encode(rs.getBytes(9)));
                car.setPrice(rs.getDouble(10));
                cars.add(car);
            }
            return cars;
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

    @Override
    public List<Car> takeAllCars(int toStartPage, int carsOnPage) throws DAOException {
        LOG.debug("CarDAOdb : takeAllCars");
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(TAKE_ALL_CARS_QUERY);
            ps.setInt(1, toStartPage);
            ps.setInt(2, carsOnPage);
            rs = ps.executeQuery();
            List<Car> cars = new ArrayList<>();
            while (rs.next()) {
                Car car = new Car();
                car.setId(rs.getInt(1));
                car.setMark(rs.getString(2));
                car.setModel(rs.getString(3));
                car.setYear(rs.getString(4));
                car.setTransmission(rs.getString(5));
                car.setType(rs.getString(6));
                car.setFuel(rs.getString(7));
                car.setInfo(rs.getString(8));
                car.setImage(Base64.encode(rs.getBytes(9)));
                car.setPrice(rs.getDouble(10));
                cars.add(car);
            }
            return cars;

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

    @Override
    public void insertCar(Car car) throws DAOException {

        LOG.debug("CarDAOdb : insertCar");
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(INSERT_CAR_QUERY);
            ps.setString(1, car.getVinCode());
            ps.setString(2, car.getModel());
            ps.setString(3, car.getGovNumber());
            ps.setString(4, car.getYear());
            ps.setString(5, car.getTransmission());
            ps.setString(6, car.getType());
            ps.setString(7, "свободна");
            ps.setString(8, car.getFuel());
            ps.setString(9, car.getInfo());
            ps.setBlob(10, new ByteArrayInputStream(Base64.decode(car.getImage())));
            ps.executeUpdate();
        } catch (Base64DecodingException | SQLException | ConnectionPoolException ex) {
            throw new DAOException(ex);
        } finally {
            try {
                connectionPooldb.closeConnection(connection, ps);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(ex);
            }
        }
    }

    @Override
    public String findCarByGovNumberVin(String govNumber, String vin) throws DAOException {
        LOG.debug("CarDAOdb : findCarByGovNumberVin");
        String model = null;
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(FIND_CAR_BY_GOV_NUMBER_VIN);
            ps.setString(1, govNumber);
            ps.setString(2, vin);
            rs = ps.executeQuery();
            if (rs.next()) {
                model = rs.getString(1);
            }
            return model;
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DAOException(ex);
        } finally {
            try {
                connectionPooldb.closeConnection(connection, ps, rs);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(ex);
            }
        }
    }

    @Override
    public void deleteCarById(int carId) throws DAOException {
        LOG.debug("CarDAOdb : deleteCarById");
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(DELETE_CAR_BY_ID);
            ps.setInt(1, carId);
            ps.executeUpdate();
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DAOException(ex);
        } finally {
            try {
                connectionPooldb.closeConnection(connection, ps);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(ex);
            }
        }
    }

    public int countAllCars() throws DAOException {
        LOG.debug("CarDAOdb : countAllCars");
        int carsAmount = 0;
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            st = connection.createStatement();
            rs = st.executeQuery(COUNT_ALL_CARS);
            while (rs.next()) {
                carsAmount = rs.getInt(1);
            }
            return carsAmount;
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DAOException(ex);
        } finally {
            try {
                connectionPooldb.closeConnection(connection, st, rs);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(ex);
            }
        }
    }

}
