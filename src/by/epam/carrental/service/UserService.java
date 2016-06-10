package by.epam.carrental.service;

import by.epam.carrental.dao.DAOFactory;
import by.epam.carrental.dao.UserDAO;
import by.epam.carrental.dao.exception.DAOException;
import by.epam.carrental.entity.User;
import by.epam.carrental.entity.ValidatorUniqueUser;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * <p>
 *      Класс UserService для произведения некоторых логических действий с сущностью User
 * </p>
 */
public class UserService {

    private static final Logger LOG = LogManager.getLogger(UserService.class.getName());
    private static final UserService instance = new UserService();
    private static final String LOGIN_START_MSG = "UserService : login";
    private static final String REGISTER_START_MSG = "UserService : register";
    private static final String TAKE_ALL_USERS_START_MSG = "UserService : takeAllUsers";
    private static final String TAKE_USER_BY_ID_START_MSG = "UserService : takeUserById";


    private UserService() {

    }

    public static UserService getInstance() {
        return instance;
    }

    /**
     * Передача данных на DAO для авторизации пользователя
     * @param login логин польщователя
     * @param password пароль пользователя
     * @return если пользователь не null, то он может авторизиоваться
     * @throws ServiceException ошибка при авторизации пользователя
     */
    public User login(String login, String password) throws ServiceException{
        LOG.debug(LOGIN_START_MSG);
        int hashPassword = password.hashCode();
        try {
            DAOFactory factory = DAOFactory.getInstance();
            UserDAO userDAO = factory.getUserDAO();
            User user = userDAO.findUser(login, hashPassword);
            return user;
        } catch(DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Передача параметров на DAO для регистрации пользователя
     * @param login логин пользователя
     * @param hashPassword хэш-значение пароля
     * @param lastName фамилия пользователя
     * @param firstName имя пользователя
     * @param middleName отчество пользователя
     * @param email e-mail пользователя
     * @param phone телефон пользователя
     * @param passport паспорт пользователя private static final String REGISTER_START_MSG
     * @param address адрес пользователя
     * @return объект класса ValidatorUniqueUser, который несет в себе информацию о причине отказа
     * в регистрации пользователя или успешной регистрации
     * @throws ServiceException ошибка при регистрации пользователя
     */
    public ValidatorUniqueUser register(String login, int hashPassword, String lastName, String firstName, String middleName,
            String email, String phone, String passport, String address) throws ServiceException {
        LOG.debug(REGISTER_START_MSG);
        User user = new User(login, hashPassword, lastName, firstName, middleName, email, phone, passport, address);
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO userDAO = factory.getUserDAO();
        try {
            ValidatorUniqueUser validatorUniqueUser = userDAO.findUser(login, email, passport);
            if (validatorUniqueUser.isUniqueLogin() && validatorUniqueUser.isUniqueEmail() && validatorUniqueUser.isUniquePassport()) {
                userDAO.addUser(user);
                return validatorUniqueUser;
            } else {
                return validatorUniqueUser;
            }
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Получение списка всех пользователей с DAO
     * @return список всех пользователей
     * @throws ServiceException ошибка при получении всех пользователей
     */
    public List<User> takeAllUsers(int pageNumber, int amountUsersOnPage) throws ServiceException {
        LOG.debug(TAKE_ALL_USERS_START_MSG);
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO dao = factory.getUserDAO();
        int startPage = userToStartPage(pageNumber, amountUsersOnPage);
        try {
            List<User> users = dao.takeAllUsers(startPage, amountUsersOnPage);
            return users;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public User findUserById(int userId) throws ServiceException {
        LOG.debug(TAKE_USER_BY_ID_START_MSG);
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO dao = factory.getUserDAO();
        try {
            User user = dao.findUserById(userId);
            return user;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public int countPageAmountAllUsers(int amountUsersOnPage) throws ServiceException {
        LOG.debug("UserService : countPageAmountAllUsers : starts");
        int pageAmount = 0;
        int usersAmount = 0;
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO dao = factory.getUserDAO();
        try {
            usersAmount = dao.countAllUsers();
            if (usersAmount % amountUsersOnPage != 0) {
                pageAmount = (usersAmount / amountUsersOnPage) + 1;
            } else {
                pageAmount = (usersAmount / amountUsersOnPage);
            }
            return pageAmount;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    private int userToStartPage(int pageNumber, int usersOnPage) {
        LOG.debug("");
        return ((pageNumber * usersOnPage) - usersOnPage);
    }
}
