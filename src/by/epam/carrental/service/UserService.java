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
        LOG.debug("UserService : login");
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
     * @param passport паспорт пользователя
     * @param address адрес пользователя
     * @return объект класса ValidatorUniqueUser, который несет в себе информацию о причине отказа
     * в регистрации пользователя или успешной регистрации
     * @throws ServiceException ошибка при регистрации пользователя
     */
    public ValidatorUniqueUser register(String login, int hashPassword, String lastName, String firstName, String middleName,
            String email, String phone, String passport, String address) throws ServiceException {
        LOG.debug("UserService : register");
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
    public List<User> takeAllUsers() throws ServiceException{
        LOG.debug("UserService : takeAllUsers");
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO dao = factory.getUserDAO();
        try {
            List<User> users = dao.takeAllUsers();
            return users;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public User findUserById(int userId) throws ServiceException {
        LOG.debug("UserService : takeUserById");
        DAOFactory factory = DAOFactory.getInstance();
        UserDAO dao = factory.getUserDAO();
        try {
            User user = dao.findUserById(userId);
            return user;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

}
