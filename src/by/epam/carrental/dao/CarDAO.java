package by.epam.carrental.dao;

import by.epam.carrental.dao.exception.DAOException;
import by.epam.carrental.entity.Car;
import by.epam.carrental.entity.CarType;

import java.util.List;

/**
 * Интерфейс, описывающий получение, обновление, удаление, вставку
 * сущностей класса Car из/в базы(у) данных
 */
public interface CarDAO {

    /**
     * Получение всех марок автомобилей из бд
     * @return список всех марок автомобилей
     * @throws DAOException ошибка при получении списка марок
     */
    public List<String> takeMarks() throws DAOException;

    /**
     * Получение списка моделей, соответствующих некоторой марке
     * @param mark марка автомобиля
     * @return список моделей автомобилей
     * @throws DAOException ошибка при получении списка моделей
     */
    public List<String> takeModels(String mark) throws DAOException;

    /**
     * Получение всех машин определенного типа
     * @param type тип автомобиля
     * @return список автоммобилей определеннго типа
     * @throws DAOException ошибка при получении списка автомобилей
     */
    public List<Car> takeCarsByType(String type, int startPage, int toStartPage) throws DAOException;

    /**
     * Получение списка свободных автоммобилей, на некотором свободном интервале времени
     * @param supposedDateFrom дата и время аренды автомобиля пользователем от
     * @param supposedDateTo дата и время аренды автомобиля пользователем до
     * @return список свободных автомобилей
     * @throws DAOException ошибка при получении списка неиспользуемых автомобилей
     */
    public List<Car> takeUnusedCars(String supposedDateFrom, String supposedDateTo,
                                    int startPage, int carsOnPage) throws DAOException;

    /**
     * Получение списка всех автомомбилей
     * @param toStartPage
     * @param startPage
     * @return список всех автоммобилей
     * @throws DAOException ошибка при получении списка всех автоммобилей
     */
    public List<Car> takeAllCars(int startPage, int toStartPage) throws DAOException;

    /**
     * Получение списка свободных на определенном временном интервале автоммобилей, определенного типа
     * @param type тип автомобиля
     * @param supposedDateFrom дата и время аренды автомобиля пользователем от
     * @param supposedDateTo дата и время аренды автомобиля пользователем до
     * @return список свободных автомобилей определенного типа
     * @throws DAOException
     */
    public List<Car> takeUnusedCarsByType(String type, String supposedDateFrom, String supposedDateTo,
                                          int startPage, int carsOnPage) throws DAOException;

    /**
     * Получсение всех типов автомоболей
     * @return список всех типов автомобилей
     * @throws DAOException ошибка при получении типов автомобилей
     */
    public List<CarType> takeCarTypes() throws DAOException;

    /**
     * Вставка автомобиля в базу данных
     * @param car вставляемый автоммобиль
     * @throws DAOException ошибка при вставке автомобиля
     */
    public void insertCar(Car car) throws DAOException;

    /**
     * Поиск модели автоммобиля по государственному номеру или vin-коду автомобиля
     * @param govNumber государственный номер автомобиля
     * @param vin vin-код автомобиля
     * @return искомая модель автомобиля
     * @throws DAOException ошибка при поиске автомобиля
     */
    public String findCarByGovNumberVin (String govNumber, String vin) throws DAOException;

    /**
     * Удаление автомобиля по его id
     * @param carId id автомобиля
     * @throws DAOException ошибка при удалении автомобиля
     */
    public void deleteCarById(int carId) throws DAOException;

    public int countAllCars() throws DAOException;

    public int countAllTypeCars(String type) throws DAOException;

    public int countUnusedTypeCars(String type, String dateFrom, String dateTo) throws DAOException;

    public Car takeCarById(int id) throws DAOException;

}
