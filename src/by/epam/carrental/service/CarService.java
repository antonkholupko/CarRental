package by.epam.carrental.service;

import by.epam.carrental.dao.CarDAO;
import by.epam.carrental.dao.DAOFactory;
import by.epam.carrental.dao.exception.DAOException;
import by.epam.carrental.entity.Car;
import by.epam.carrental.entity.CarType;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.ws.Service;
import java.util.List;

/**
 *
 * <p>
 *      Класс CarService используется для произведения некоторых логических действий с
 *      сущностями класса Car.
 *</p>
 */
public class CarService {

    private static final Logger LOG = LogManager.getLogger(CarService.class.getName());

    private static final CarService instance = new CarService();

    private CarService() {

    }

    public static CarService getInstance() {
        return instance;
    }

    /**
     * получение списка марок автомобилей с DAO
     * @return список марок автомобилей
     * @throws ServiceException ошибка при получении списка марок автомобилей
     */
    public List<String> takeMarks() throws ServiceException{
        LOG.debug("CarService : takeMarks");
        try {
            DAOFactory factory = DAOFactory.getInstance();
            CarDAO dao = factory.getCarDAO();
            List<String> marks = dao.takeMarks();
            return marks;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Получение списка моделей автомобилей
     * @param mark марка автомобиля
     * @return список моделей автомобилей, соответстующих марке автомобиля
     * @throws ServiceException  ошибка при получении списка моделей автомобилей
     */
    public List<String> takeModels(String mark) throws ServiceException {
        LOG.debug("CarService : takeModels");
        try {
            DAOFactory factory = DAOFactory.getInstance();
            CarDAO dao = factory.getCarDAO();
            List<String> models = dao.takeModels(mark);
            return models;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Получение всех типов автомобилей с DAO
     * @return список всех типов автомобилей
     * @throws ServiceException ошибка при получении всех типов автомобилей
     */
    public List<CarType> takeCarTypes() throws ServiceException {
        LOG.debug("CarService : takeCarTypes");
        try {
            DAOFactory factory = DAOFactory.getInstance();
            CarDAO dao = factory.getCarDAO();
            List<CarType> carTypes = dao.takeCarTypes();
            return carTypes;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Получение всех свободных автомобилей определенного типа в некотором временном
     * промежутке
     * @param type тип автомобиля
     * @param dateFrom дата получения автомобиля пользователем
     * @param dateTo дата возврата автомобиля пользователем
     * @return список свободных автомобилей определенного типа в некотором временном
     * промежутке
     * @throws ServiceException ошибка при получении списка свободных автомобилей
     */
    public List<Car> takeCarsByTypeAndDate(String type, String dateFrom, String dateTo) throws ServiceException{
        LOG.debug("CarService : takeCarsByType");
        try {
            DAOFactory factory = DAOFactory.getInstance();
            CarDAO dao = factory.getCarDAO();
            if (type.equals("All") && dateFrom != null && dateTo != null) {
                List<Car> cars = dao.takeUnusedCars(dateFrom, dateTo);
                return cars;
            } /*else if (type.equals("All") && dateFrom == null && dateTo == null) {
                List<Car> cars = dao.takeAllCars();
                return cars;
            } */else if (dateFrom == null || dateTo == null) {
                List<Car> cars = dao.takeCarsByType(type);
                return cars;
            } else {
                List<Car> cars = dao.takeUnusedCarsByType(type, dateFrom, dateTo);
                return cars;
            }
        } catch(DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Получение всех свободных автомобилей в определенном временном промежутке с DAO
     * @param carType тип автомобиля
     * @param supposedDateFrom дата получения автомобиля пользователем
     * @param supposedDateTo дата возврата автомобиля пользователем
     * @param pageNumber
     * @param carsOnPage
     * @return список свободных автомобилей в определенном временном промежутке
     * @throws ServiceException ошибка при получении списка автомобилей
     */
    public List<Car> takeUnusedCars(String carType, String supposedDateFrom, String supposedDateTo,
                                    int pageNumber, int carsOnPage) throws ServiceException{
        LOG.debug("CarService : takeUnusedCars");
        /*int carToStart = carToStartPage(pageNumber, carsOnPage);*/
        try {
            DAOFactory factory = DAOFactory.getInstance();
            CarDAO dao = factory.getCarDAO();
            if (carType.equals("All")) {
                List<Car> cars = dao.takeUnusedCars(supposedDateFrom, supposedDateTo);
                return cars;
            } else {
                List<Car> cars = dao.takeUnusedCarsByType(carType, supposedDateFrom, supposedDateTo);
                return cars;
            }
        } catch(DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Получение списка всех автомобилей с DAO
     * @param pageNumber
     * @param carsOnPage
     * @return список всех автомобилей
     * @throws ServiceException ошибка при получении списка автомобилей
     */
    public List<Car> takeAllCars(int pageNumber, int carsOnPage) throws ServiceException {
        LOG.debug("CarService : takeAllCars");
        DAOFactory factory = DAOFactory.getInstance();
        int startPage = carToStartPage(pageNumber, carsOnPage);
        CarDAO dao = factory.getCarDAO();
        try {
            List<Car> cars = dao.takeAllCars(startPage, carsOnPage);
            return cars;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Передача автомобиля для вставки на DAO
     * @param car автомобиль для вставки
     * @throws ServiceException ошибка при вставке автомобиля
     */
    public void insertCar(Car car) throws ServiceException{
        LOG.debug("CarService : insertCar");
        DAOFactory factory = DAOFactory.getInstance();
        CarDAO dao = factory.getCarDAO();
        try {
            dao.insertCar(car);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Передача id автомобиля на DAO, который необходимо удалить
     * @param carId id автомобиля
     * @throws ServiceException ошибка при удалении автомобиля
     */
    public void deleteCar(int carId) throws ServiceException {
        LOG.debug("CarService : deleteCar");
        DAOFactory factory = DAOFactory.getInstance();
        CarDAO dao = factory.getCarDAO();
        try {
            dao.deleteCarById(carId);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public int countPageAmountAllCars(int amountCarsOnPage) throws ServiceException{
        LOG.debug("CarService : countPageAmountAllCars");
        int pageAmount = 0;
        int carsAmount = 0;
        DAOFactory factory = DAOFactory.getInstance();
        CarDAO dao = factory.getCarDAO();
        try {
            carsAmount = dao.countAllCars();
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
        pageAmount = (carsAmount / amountCarsOnPage) + 1;
        return pageAmount;
    }

    private int carToStartPage(int pageNumber, int carsOnPage) {
        LOG.debug("CarService : carToStartPage");
        return ((pageNumber * carsOnPage) - carsOnPage);
    }
}
