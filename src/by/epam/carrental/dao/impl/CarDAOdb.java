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
            "WHERE cars.type=? GROUP BY carID DESC LIMIT ?,?;";

    private static final String TAKE_CARS_UNUSED_QUERY = "SELECT cars.carID, models.mark, cars.model, cars.year, " +
            "cars.transmission, cars.type, cars.fuel, cars.info, cars.image, cartypes.price " +
            "FROM cars INNER JOIN models ON models.model = cars.model INNER JOIN cartypes ON cars.type = cartypes.type " +
            "WHERE carID NOT IN " +
            "(SELECT carID FROM orders WHERE " +
            "(NOT (orders.status='canceled' OR orders.status='rejected' OR orders.status='closed' OR orders.status='returned')) AND " +
            "((? BETWEEN orders.supposedFromDate AND orders.supposedToDate) OR " +
            "(? BETWEEN orders.supposedFromDate AND orders.supposedToDate) OR " +
            "(? BETWEEN orders.supposedFromDate AND orders.supposedToDate) OR" +
            "((? BETWEEN orders.supposedFromDate AND orders.supposedToDate) " +
            "AND (? BETWEEN orders.supposedFromDate AND orders.supposedToDate)) OR " +
            "((orders.supposedFromDate BETWEEN ? AND ?)AND(orders.supposedToDate " +
            "BETWEEN ? AND ?)))) GROUP BY carID DESC LIMIT ?,?;";

    private static final String TAKE_CARS_UNUSED_BY_TYPE_QUERY = "SELECT cars.carID, models.mark, cars.model, cars.year, " +
            "cars.transmission, cars.type, cars.fuel, cars.info, cars.image, cartypes.price " +
            "FROM cars INNER JOIN models ON models.model = cars.model INNER JOIN cartypes ON cars.type = cartypes.type " +
            "WHERE carID NOT IN " +
            "(SELECT carID FROM orders WHERE " +
            "(NOT (orders.status='canceled' OR orders.status='rejected' OR orders.status='closed' OR orders.status='returned')) AND " +
            "((? BETWEEN orders.supposedFromDate AND orders.supposedToDate) OR " +
            "(? BETWEEN orders.supposedFromDate AND orders.supposedToDate) OR " +
            "(? BETWEEN orders.supposedFromDate AND orders.supposedToDate) OR" +
            "((? BETWEEN orders.supposedFromDate AND orders.supposedToDate) " +
            "AND (? BETWEEN orders.supposedFromDate AND orders.supposedToDate)) OR " +
            "((orders.supposedFromDate BETWEEN ? AND ?)AND(orders.supposedToDate " +
            "BETWEEN ? AND ?)))) AND cars.type=? GROUP BY carID DESC LIMIT ?,?;";
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
    private static final String COUNT_ALL_TYPE_CARS = "SELECT COUNT(carID) FROM cars WHERE type=?;";
    private static final String COUNT_UNUSED_TYPE_CARS = "SELECT COUNT(carID) FROM cars " +
            "WHERE carID NOT IN " +
            "(SELECT carID FROM orders WHERE " +
            "(NOT (orders.status='canceled' OR orders.status='rejected' OR orders.status='closed' OR orders.status='returned')) AND " +
            "((? BETWEEN orders.supposedFromDate AND orders.supposedToDate) OR " +
            "(? BETWEEN orders.supposedFromDate AND orders.supposedToDate) OR " +
            "(? BETWEEN orders.supposedFromDate AND orders.supposedToDate) OR" +
            "((? BETWEEN orders.supposedFromDate AND orders.supposedToDate) " +
            "AND (? BETWEEN orders.supposedFromDate AND orders.supposedToDate)) OR " +
            "((orders.supposedFromDate BETWEEN ? AND ?)AND(orders.supposedToDate " +
            "BETWEEN ? AND ?)))) AND cars.type=?;";
    private static final String TAKE_CAR_BY_ID_QUERY = "SELECT models.mark, cars.model, cars.govNumber, " +
            "cars.year, cars.transmission, cars.type, cars.status, cars.fuel, cars.info, cars.image, cartypes.price " +
            "FROM cars " +
            "INNER JOIN models ON models.model = cars.model " +
            "INNER JOIN cartypes ON cars.type = cartypes.type " +
            "WHERE carID=?;";

    private static final String CAR_FREE_STATUS = "unused";

    private static final String TAKE_MARKS_STARTS_MSG = "CarDAOdb : takeMarks : starts";
    private static final String TAKE_MODELS_STARTS_MSG = "CarDAOdb : takeModels : starts";
    private static final String TAKE_CAR_TYPES_STARTS_MSG = "CarDAOdb : takeCarTypes : starts";
    private static final String TAKE_CARS_BY_TYPE_STARTS_MSG = "CarDAOdb : takeCarsByType : starts";
    private static final String TAKE_UNUSED_CARS_STARTS_MSG = "CarDAOdb : takeUnusedCars : starts";
    private static final String TAKE_UNUSED_CARS_BY_TYPE_STARTS_MSG = "CarDAOdb : takeUnusedCarsByType : starts";
    private static final String TAKE_ALL_CARS_STARTS_MSG = "CarDAOdb : takeAllCars : starts";
    private static final String INSERT_CAR_STARTS_MSG = "CarDAOdb : insertCar : starts";
    private static final String FIND_CAR_BY_VIN_NUMBER_STARTS_MSG = "CarDAOdb : findCarByGovNumberVin : starts";
    private static final String DELETE_CAR_BY_ID_STARTS_MSG = "CarDAOdb : deleteCarById : starts";
    private static final String COUNT_ALL_CARS_STARTS_MSG = "CarDAOdb : countAllCars : starts";
    private static final String COUNT_ALL_TYPE_CARS_STARTS_MSG = "CarDAOdb : countAllCars : starts";
    private static final String COUNT_UNUSED_TYPE_CARS_STARTS_MSG = "CarDAOdb : countUnusedTypeCars : starts";
    private static final String TAKE_CAR_BY_ID_STARTS_MSG = "CarDAOdb : takeCarById : starts";
    private static final String TAKE_MARKS_ENDS_MSG = "CarDAOdb : takeMarks : ends";
    private static final String TAKE_MODELS_ENDS_MSG = "CarDAOdb : takeModels : ends";
    private static final String TAKE_CAR_TYPES_ENDS_MSG = "CarDAOdb : takeCarTypes : ends";
    private static final String TAKE_CARS_BY_TYPE_ENDS_MSG = "CarDAOdb : takeCarsByType : ends";
    private static final String TAKE_UNUSED_CARS_ENDS_MSG = "CarDAOdb : takeUnusedCars : ends";
    private static final String TAKE_UNUSED_CARS_BY_TYPE_ENDS_MSG = "CarDAOdb : takeUnusedCarsByType : ends";
    private static final String TAKE_ALL_CARS_ENDS_MSG = "CarDAOdb : takeAllCars : ends";
    private static final String INSERT_CAR_ENDS_MSG = "CarDAOdb : insertCar : ends";
    private static final String FIND_CAR_BY_VIN_NUMBER_ENDS_MSG = "CarDAOdb : findCarByGovNumberVin : ends";
    private static final String DELETE_CAR_BY_ID_ENDS_MSG = "CarDAOdb : deleteCarById : ends";
    private static final String COUNT_ALL_CARS_ENDS_MSG = "CarDAOdb : countAllCars : ends";
    private static final String COUNT_ALL_TYPE_CARS_ENDS_MSG = "CarDAOdb : countAllCars : ends";
    private static final String COUNT_UNUSED_TYPE_CARS_ENDS_MSG = "CarDAOdb : countUnusedTypeCars : ends";
    private static final String TAKE_CAR_BY_ID_ENDS_MSG = "CarDAOdb : takeCarById : ends";

    private static final String TAKE_MARKS_ERROR_MSG = "CarDAOdb : takeMarks : ERROR";
    private static final String TAKE_MARKS_CLOSE_CON_ERROR_MSG = "CarDAOdb : takeMarks : close connection error";
    private static final String TAKE_MODELS_ERROR_MSG = "CarDAOdb : takeModels : ERROR";
    private static final String TAKE_MODELS_CLOSE_CON_ERROR_MSG = "CarDAOdb : takeModels : close connection error";
    private static final String TAKE_CAR_TYPES_ERROR_MSG = "CarDAOdb : takeCarTypes : ERROR";
    private static final String TAKE_CAR_TYPES_CLOSE_CON_ERROR_MSG = "CarDAOdb : takeCarTypes : close connection error";
    private static final String TAKE_CARS_BY_TYPE_ERROR_MSG = "CarDAOdb : takeCarsByType : ERROR";
    private static final String TAKE_CARS_BY_TYPE_CLOSE_CON_ERROR_MSG = "CarDAOdb : takeCarsByType : close connection error";
    private static final String TAKE_UNUSED_CARS_ERROR_MSG = "CarDAOdb : takeUnusedCars : ERROR";
    private static final String TAKE_UNUSED_CARS_CLOSE_CON_ERROR_MSG = "CarDAOdb : takeUnusedCars : close connection error";
    private static final String TAKE_UNUSED_CARS_BY_TYPE_ERROR_MSG = "CarDAOdb : takeUnusedCarsByType : ERROR";
    private static final String TAKE_UNUSED_CARS_BY_TYPE_CLOSE_CON_ERROR_MSG = "CarDAOdb : takeUnusedCarsByType : close connection error";
    private static final String TAKE_ALL_CARS_ERROR_MSG = "CarDAOdb : takeAllCars : ERROR";
    private static final String TAKE_ALL_CARS_CLOSE_CON_ERROR_MSG = "CarDAOdb : takeAllCars : close connection error";
    private static final String INSERT_CAR_ERROR_MSG = "CarDAOdb : insertCar : ERROR";
    private static final String INSERT_CAR_CLOSE_CON_ERROR_MSG = "CarDAOdb : insertCar : close connection error";
    private static final String FIND_CAR_BY_GOV_NUMBER_VIN_ERROR_MSG = "CarDAOdb : findCarByGovNumberVin : ERROR";
    private static final String FIND_CAR_BY_GOV_NUMBER_VIN_CLOSE_CON_ERROR_MSG = "CarDAOdb : findCarByGovNumberVin : close connection error";
    private static final String DELETE_CAR_BY_ID_ERROR_MSG = "CarDAOdb : deleteCarById : ERROR";
    private static final String DELETE_CAR_BY_ID_CLOSE_CON_ERROR_MSG = "CarDAOdb : deleteCarById : close connection error";
    private static final String COUNT_ALL_CARS_ERROR_MSG = "CarDAOdb : countAllCars : ERROR";
    private static final String COUNT_ALL_CARS_CLOSE_CON_ERROR_MSG = "CarDAOdb : countAllCars : close connection error";
    private static final String COUNT_ALL_TYPE_CARS_ERROR_MSG = "CarDAOdb : countAllTypeCars : ERROR";
    private static final String COUNT_ALL_TYPE_CARS_CLOSE_CON_ERROR_MSG = "CarDAOdb : countAllTypeCars : close connection error";
    private static final String COUNT_UNUSED_TYPE_CARS_ERROR_MSG = "carDAOdb : countUnusedTypeCars : ERROR";
    private static final String COUNT_UNUSED_TYPE_CARS_CLOSE_CON_ERROR_MSG = "carDAOdb : countUnusedTypeCars : close connection error";
    private static final String TAKE_CAR_BY_ID_ERROR_MSG = "CarDAOdb : TakeCarById : ERROR";
    private static final String TAKE_CAR_BY_ID_CLOSE_CON_ERROR_MSG = "CarDAOdb : TakeCarById : close connection error";

    @Override
    public List<String> takeMarks() throws DAOException {
        LOG.debug(TAKE_MARKS_STARTS_MSG);
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
            throw new DAOException(TAKE_MARKS_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, st, rs);
                }
                LOG.debug(TAKE_MARKS_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(TAKE_MARKS_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    @Override
    public List<String> takeModels(String mark) throws DAOException {
        LOG.debug(TAKE_MODELS_STARTS_MSG);
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
            throw new DAOException(TAKE_MODELS_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(TAKE_MODELS_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(TAKE_MODELS_CLOSE_CON_ERROR_MSG, ex);
            }
        }

    }

    @Override
    public List<CarType> takeCarTypes() throws DAOException {
        LOG.debug(TAKE_CAR_TYPES_STARTS_MSG);
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
            throw new DAOException(TAKE_CAR_TYPES_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, st, rs);
                }
                LOG.debug(TAKE_CAR_TYPES_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(TAKE_CAR_TYPES_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    @Override
    public List<Car> takeCarsByType(String type, int toStartPage, int carsOnPage) throws DAOException {
        LOG.debug(TAKE_CARS_BY_TYPE_STARTS_MSG);
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
            ps.setInt(2, toStartPage);
            ps.setInt(3, carsOnPage);
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
            throw new DAOException(TAKE_CARS_BY_TYPE_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(TAKE_CARS_BY_TYPE_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(TAKE_CARS_BY_TYPE_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    @Override
    public List<Car> takeUnusedCars(String supposedDateFrom, String supposedDateTo,
                                    int startPage, int carsOnPage) throws DAOException {
        LOG.debug(TAKE_UNUSED_CARS_STARTS_MSG);
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
            ps.setInt(10, startPage);
            ps.setInt(11, carsOnPage);
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
            throw new DAOException(TAKE_UNUSED_CARS_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(TAKE_UNUSED_CARS_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(TAKE_UNUSED_CARS_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    @Override
    public List<Car> takeUnusedCarsByType(String type, String supposedDateFrom, String supposedDateTo,
                                          int startPage, int carsOnPage) throws DAOException {
        LOG.debug(TAKE_UNUSED_CARS_BY_TYPE_STARTS_MSG);
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
            ps.setInt(11, startPage);
            ps.setInt(12, carsOnPage);
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
            throw new DAOException(TAKE_UNUSED_CARS_BY_TYPE_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(TAKE_UNUSED_CARS_BY_TYPE_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(TAKE_UNUSED_CARS_BY_TYPE_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    @Override
    public List<Car> takeAllCars(int toStartPage, int carsOnPage) throws DAOException {
        LOG.debug(TAKE_ALL_CARS_STARTS_MSG);
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
            throw new DAOException(TAKE_ALL_CARS_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(TAKE_ALL_CARS_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(TAKE_ALL_CARS_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    @Override
    public void insertCar(Car car) throws DAOException {

        LOG.debug(INSERT_CAR_STARTS_MSG);
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
            ps.setString(7, CAR_FREE_STATUS);
            ps.setString(8, car.getFuel());
            ps.setString(9, car.getInfo());
            ps.setBlob(10, new ByteArrayInputStream(Base64.decode(car.getImage())));
            ps.executeUpdate();
        } catch (Base64DecodingException | SQLException | ConnectionPoolException ex) {
            throw new DAOException(INSERT_CAR_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps);
                }
                LOG.debug(INSERT_CAR_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(INSERT_CAR_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    @Override
    public String findCarByGovNumberVin(String govNumber, String vin) throws DAOException {
        LOG.debug(FIND_CAR_BY_VIN_NUMBER_STARTS_MSG);
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
            throw new DAOException(FIND_CAR_BY_GOV_NUMBER_VIN_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(FIND_CAR_BY_VIN_NUMBER_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(FIND_CAR_BY_GOV_NUMBER_VIN_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    @Override
    public void deleteCarById(int carId) throws DAOException {
        LOG.debug(DELETE_CAR_BY_ID_STARTS_MSG);
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
            throw new DAOException(DELETE_CAR_BY_ID_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps);
                }
                LOG.debug(DELETE_CAR_BY_ID_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(DELETE_CAR_BY_ID_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    public int countAllCars() throws DAOException {
        LOG.debug(COUNT_ALL_CARS_STARTS_MSG);
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
            throw new DAOException(COUNT_ALL_CARS_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, st, rs);
                }
                LOG.debug(COUNT_ALL_CARS_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(COUNT_ALL_CARS_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    public int countAllTypeCars(String type) throws DAOException {
        LOG.debug(COUNT_ALL_TYPE_CARS_STARTS_MSG);
        int carsAmount = 0;
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(COUNT_ALL_TYPE_CARS);
            ps.setString(1, type);
            rs = ps.executeQuery();
            while (rs.next()) {
                carsAmount = rs.getInt(1);
            }
            return carsAmount;
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DAOException(COUNT_ALL_TYPE_CARS_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(COUNT_ALL_TYPE_CARS_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(COUNT_ALL_TYPE_CARS_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    public int countUnusedTypeCars(String type, String dateFrom, String dateTo) throws DAOException {
        LOG.debug(COUNT_UNUSED_TYPE_CARS_STARTS_MSG);
        int carsAmount = 0;
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(COUNT_UNUSED_TYPE_CARS);
            ps.setString(1, dateFrom);
            ps.setString(2, dateTo);
            ps.setString(3, dateTo);
            ps.setString(4, dateFrom);
            ps.setString(5, dateTo);
            ps.setString(6, dateFrom);
            ps.setString(7, dateTo);
            ps.setString(8, dateFrom);
            ps.setString(9, dateTo);
            ps.setString(10, type);
            rs = ps.executeQuery();
            while (rs.next()) {
                carsAmount = rs.getInt(1);
            }
            return carsAmount;
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DAOException(COUNT_UNUSED_TYPE_CARS_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(COUNT_UNUSED_TYPE_CARS_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(COUNT_UNUSED_TYPE_CARS_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    public Car takeCarById(int id) throws DAOException {
        LOG.debug(TAKE_CAR_BY_ID_STARTS_MSG);
        Car car = new Car();
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(TAKE_CAR_BY_ID_QUERY);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                car.setMark(rs.getString(1));
                car.setModel(rs.getString(2));
                car.setGovNumber(rs.getString(3));
                car.setYear(rs.getString(4));
                car.setTransmission(rs.getString(5));
                car.setType(rs.getString(6));
                car.setStatus(rs.getString(7));
                car.setFuel(rs.getString(8));
                car.setInfo(rs.getString(9));
                car.setImage(Base64.encode(rs.getBytes(10)));
                car.setPrice(rs.getDouble(11));
            }
            return car;
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DAOException(TAKE_CAR_BY_ID_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(TAKE_CAR_BY_ID_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(TAKE_CAR_BY_ID_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

}
