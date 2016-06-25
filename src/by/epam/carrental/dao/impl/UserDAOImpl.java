package by.epam.carrental.dao.impl;

import by.epam.carrental.dao.DAOStringConstant;
import by.epam.carrental.dao.UserDAO;
import by.epam.carrental.dao.connectionpoolhelper.exception.ConnectionPoolException;
import by.epam.carrental.dao.exception.DAOException;
import by.epam.carrental.entity.User;
import by.epam.carrental.entity.ValidatorUniqueUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для получения/вставки сущностей класса User из/в базы(у) данных
 */
public class UserDAOImpl implements UserDAO {

    private static final String FIND_USER_QUERY = "SELECT userID, login, password, type, lastName, firstName," +
            " middleName, email, phone, passport, address FROM users WHERE login=? AND password=?;";
    private static final String FIND_USER_BY_LOGIN_QUERY = "SELECT login FROM users WHERE login=?;";
    private static final String FIND_USER_BY_PASSPORT_QUERY = "SELECT login FROM users WHERE passport=?;";
    private static final String FIND_USER_BY_EMAIL_QUERY = "SELECT login FROM users WHERE email=?";
    private static final String ADD_USER_QUERY = "INSERT INTO users (login, password, type, lastName," +
            " firstName, middleName, email, phone, passport, address) VALUES (?,?,?,?,?,?,?,?,?,?);";
    private static final String TAKE_ALL_USERS_QUERY = "SELECT userID, login, type, lastName, firstName, " +
            "email, phone FROM users GROUP BY (userID) DESC LIMIT ?,?;";
    private static final String FIND_USER_BY_ID_QUERY = "SELECT * FROM users WHERE userID=?;";
    private static final String COUNT_ALL_USERS_QUERY = "SELECT COUNT(userID) FROM users;";

    private static final Logger LOG = LogManager.getLogger(UserDAOImpl.class.getName());


    @Override
    public User findUser(String login, int hashPassword) throws DAOException{
        LOG.debug(DAOStringConstant.DAO_FIND_USER_STARTS_MSG);
        User user = null;
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(FIND_USER_QUERY);
            ps.setString(1, login);
            ps.setInt(2, hashPassword);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setHashPassword(rs.getInt(3));
                user.setType(rs.getString(4));
                user.setLastName(rs.getString(5));
                user.setFirstName(rs.getString(6));
                user.setMiddleName(rs.getString(7));
                user.setEMail(rs.getString(8));
                user.setPhone(rs.getString(9));
                user.setPassport(rs.getString(10));
                user.setAddress(rs.getString(11));
            }
            return user;
        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException(DAOStringConstant.DAO_FIND_USER_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(DAOStringConstant.DAO_FIND_USER_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(DAOStringConstant.DAO_FIND_USER_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    @Override
    public ValidatorUniqueUser findUser(String login, String email, String passport) throws DAOException{
        LOG.debug(DAOStringConstant.DAO_FIND_USER_STARTS_MSG);
        ValidatorUniqueUser validatorUniqueUser = new ValidatorUniqueUser();
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(FIND_USER_BY_LOGIN_QUERY);
            ps.setString(1, login);
            rs = ps.executeQuery();
            if (rs.next()) {
                validatorUniqueUser.setUniqueLogin(false);
            } else {
                validatorUniqueUser.setUniqueLogin(true);
                }
            ps = connection.prepareStatement(FIND_USER_BY_PASSPORT_QUERY);
            ps.setString(1, passport);
            rs = ps.executeQuery();
            if (rs.next()) {
                validatorUniqueUser.setUniqueEmail(false);
            } else {
                validatorUniqueUser.setUniqueEmail(true);
            }
            ps = connection.prepareStatement(FIND_USER_BY_EMAIL_QUERY);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                validatorUniqueUser.setUniquePassport(false);
            } else {
                validatorUniqueUser.setUniquePassport(true);
            }
        } catch (ConnectionPoolException | SQLException ex) {
            throw new DAOException(DAOStringConstant.DAO_FIND_USER_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(DAOStringConstant.DAO_FIND_USER_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(DAOStringConstant.DAO_FIND_USER_CLOSE_CON_ERROR_MSG, ex);
            }
        }
        return validatorUniqueUser;
    }

    @Override
    public void addUser(User user) throws DAOException{
        LOG.debug(DAOStringConstant.DAO_ADD_USER_STARTS_MSG);
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(ADD_USER_QUERY);
            ps.setString(1, user.getLogin());
            ps.setInt(2, user.getHashPassword());
            ps.setString(3, "user");
            ps.setString(4, user.getLastName());
            ps.setString(5, user.getFirstName());
            ps.setString(6, user.getMiddleName());
            ps.setString(7, user.getEMail());
            ps.setString(8, user.getPhone());
            ps.setString(9, user.getPassport());
            ps.setString(10, user.getAddress());
            ps.executeUpdate();
        } catch(ConnectionPoolException | SQLException ex) {
            throw new DAOException(DAOStringConstant.DAO_ADD_USER_ERROR_MSG, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps);
                }
                LOG.debug(DAOStringConstant.DAO_ADD_USER_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(DAOStringConstant.DAO_ADD_USER_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    @Override
    public List<User> takeAllUsers(int startPage, int amountUsersOnPage) throws DAOException {
        LOG.debug(DAOStringConstant.DAO_TAKE_ALL_USERS_STARTS_MSG);
        List<User> users = new ArrayList<>();
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(TAKE_ALL_USERS_QUERY);
            ps.setInt(1, startPage);
            ps.setInt(2, amountUsersOnPage);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setType(rs.getString(3));
                user.setLastName(rs.getString(4));
                user.setFirstName(rs.getString(5));
                user.setEMail(rs.getString(6));
                user.setPhone(rs.getString(7));
                users.add(user);
            }
            return users;
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DAOException(DAOStringConstant.DAO_TAKE_ALL_USERS_ERROR, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(DAOStringConstant.DAO_TAKE_ALL_USERS_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(DAOStringConstant.DAO_TAKE_ALL_USERS_CLOSE_CON_ERROR_MSG, ex);
            }
        }
    }

    @Override
    public User findUserById(int userId) throws DAOException {
        LOG.debug(DAOStringConstant.DAO_FIND_USER_BY_ID_STARTS_MSG);
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        User user = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            ps = connection.prepareStatement(FIND_USER_BY_ID_QUERY);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt(1));
                user.setLogin(rs.getString(2));
                user.setType(rs.getString(4));
                user.setLastName(rs.getString(5));
                user.setFirstName(rs.getString(6));
                user.setMiddleName(rs.getString(7));
                user.setEMail(rs.getString(8));
                user.setPhone(rs.getString(9));
                user.setPassport(rs.getString(10));
                user.setAddress(rs.getString(11));
            }
            return user;
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DAOException(DAOStringConstant.DAO_FIND_USER_BY_ID_ERROR, ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, ps, rs);
                }
                LOG.debug(DAOStringConstant.DAO_FIND_USER_BY_ID_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(DAOStringConstant.DAO_FIND_USER_BY_ID_CLOSE_CON_ERROR, ex);
            }
        }
    }

    public int countAllUsers() throws DAOException {
        LOG.debug(DAOStringConstant.DAO_COUNT_ALL_USERS_STARTS_MSG);
        int ordersAmount = 0;
        Connection connection = null;
        ConnectionPooldb connectionPooldb = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            connectionPooldb = ConnectionPooldb.getInstance();
            connection = connectionPooldb.takeConnection();
            st = connection.createStatement();
            rs = st.executeQuery(COUNT_ALL_USERS_QUERY);
            while (rs.next()) {
                ordersAmount = rs.getInt(1);
            }
            return ordersAmount;
        } catch (SQLException | ConnectionPoolException ex) {
            throw new DAOException(ex);
        } finally {
            try {
                if (connectionPooldb != null) {
                    connectionPooldb.closeConnection(connection, st, rs);
                }
                LOG.debug(DAOStringConstant.DAO_COUNT_ALL_USERS_ENDS_MSG);
            } catch (ConnectionPoolException ex) {
                throw new DAOException(ex);
            }
        }
    }
}

