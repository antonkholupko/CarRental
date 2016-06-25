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

    private final UserDAO USER_DAO = DAOFactory.getInstance().getUserDAO();

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
        LOG.debug(ServiceConstant.LOGIN_START_MSG);
        int hashPassword = password.hashCode();
        try {
            User user = USER_DAO.findUser(login, hashPassword);
            LOG.debug(ServiceConstant.LOGIN_END_MSG);
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
        LOG.debug(ServiceConstant.REGISTER_START_MSG);
        User user = new User(login, hashPassword, lastName, firstName, middleName, email, phone, passport, address);
        try {
            ValidatorUniqueUser validatorUniqueUser = USER_DAO.findUser(login, email, passport);
            if (validatorUniqueUser.isUniqueLogin() && validatorUniqueUser.isUniqueEmail() && validatorUniqueUser.isUniquePassport()) {
                USER_DAO.addUser(user);
                LOG.debug(ServiceConstant.REGISTER_END_MSG);
                return validatorUniqueUser;
            } else {
                LOG.debug(ServiceConstant.REGISTER_END_MSG);
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
        LOG.debug(ServiceConstant.TAKE_ALL_USERS_START_MSG);
        int startPage = userToStartPage(pageNumber, amountUsersOnPage);
        try {
            List<User> users = USER_DAO.takeAllUsers(startPage, amountUsersOnPage);
            LOG.debug(ServiceConstant.TAKE_ALL_USERS_END_MSG);
            return users;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public User findUserById(int userId) throws ServiceException {
        LOG.debug(ServiceConstant.TAKE_USER_BY_ID_START_MSG);
        try {
            User user = USER_DAO.findUserById(userId);
            LOG.debug(ServiceConstant.TAKE_USER_BY_ID_END_MSG);
            return user;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public int countPageAmountAllUsers(int amountUsersOnPage) throws ServiceException {
        LOG.debug(ServiceConstant.COUNT_PAGE_AMOUNT_ALL_USERS_START_MSG);
        int pageAmount = 0;
        int usersAmount = 0;
        try {
            usersAmount = USER_DAO.countAllUsers();
            if (usersAmount % amountUsersOnPage != 0) {
                pageAmount = (usersAmount / amountUsersOnPage) + 1;
            } else {
                pageAmount = (usersAmount / amountUsersOnPage);
            }
            LOG.debug(ServiceConstant.COUNT_PAGE_AMOUNT_ALL_USERS_END_MSG);
            return pageAmount;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    private int userToStartPage(int pageNumber, int usersOnPage) {
        LOG.debug(ServiceConstant.USER_TO_START_PAGE_STARTS_MSG);
        LOG.debug(ServiceConstant.USER_TO_START_PAGE_ENDS_MSG);
        return ((pageNumber * usersOnPage) - usersOnPage);
    }
}
