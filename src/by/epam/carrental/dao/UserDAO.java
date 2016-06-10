package by.epam.carrental.dao;

import by.epam.carrental.dao.exception.DAOException;
import by.epam.carrental.entity.User;
import by.epam.carrental.entity.ValidatorUniqueUser;

import java.util.List;

/**
 * Интерфейс, описывающий получение, вставку
 * сущностей класса User из/в базы(у) данных
 */
public interface UserDAO {

    /**
     * Поиск пользователя по логину и паролю
     * @param login логин пользователя
     * @param hashPassword хэш-значение пароля пользователя
     * @return найденный пользователь
     * @throws DAOException ошибка при поиске пользователя
     */
    public User findUser(String login, int hashPassword) throws DAOException;

    /**
     * Проверка наличия таких логина, e-mail, пароля в базе данных
     * @param login логин пользователя
     * @param email e-mail пользователя
     * @param passport серия и номер пасспорта пользователя
     * @return объект класса ValidatorUniqueUser, хранящий в себе информацию, об уникальности
     * вставляемых данных
     * @throws DAOException ошибка при проверке наличия таких логина,
     * e-mail, пароля в бд
     */
    public ValidatorUniqueUser findUser(String login, String email, String passport) throws DAOException;

    /**
     * Добавление пользователя
     * @param user добавляемый пользователь
     * @throws DAOException ошибка при добавлении пользователя
     */
    public void addUser(User user) throws DAOException;

    /**
     * Получение всех пользователей
     * @return список всех пользователей
     * @throws DAOException ошибка при получении всех пользователей
     */
    public List<User> takeAllUsers(int startPage, int amountUsersOnPage) throws DAOException;

    /**
     * Поиск пользователя по его id
     * @param userId id пользователя
     * @return найденный пользователь
     * @throws DAOException ошибка при поиске пользователя
     */
    public User findUserById(int userId) throws DAOException;

    public int countAllUsers() throws DAOException;

}
