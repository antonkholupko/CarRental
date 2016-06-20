package by.epam.carrental.service;

import by.epam.carrental.dao.CarDAO;
import by.epam.carrental.dao.DAOFactory;
import by.epam.carrental.dao.exception.DAOException;
import by.epam.carrental.entity.Car;
import by.epam.carrental.entity.CarType;
import by.epam.carrental.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * <p>
 * Класс CarService используется для произведения некоторых логических действий с
 * сущностями класса Car.
 * </p>
 */
public class CarService {

    private static final Logger LOG = LogManager.getLogger(CarService.class.getName());

    private static final CarService instance = new CarService();

    private static final CarDAO CAR_DAO = DAOFactory.getInstance().getCarDAO();

    private CarService() {

    }

    public static CarService getInstance() {
        return instance;
    }

    /**
     * получение списка марок автомобилей с DAO
     *
     * @return список марок автомобилей
     * @throws ServiceException ошибка при получении списка марок автомобилей
     */
    public List<String> takeMarks() throws ServiceException {
        LOG.debug(ServiceStringConstant.SERVICE_TAKE_MARKS_STARTS_MSG);
        try {
            List<String> marks = CAR_DAO.takeMarks();
            LOG.debug(ServiceStringConstant.SERVICE_TAKE_MARKS_ENDS_MSG);
            return marks;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Получение списка моделей автомобилей
     *
     * @param mark марка автомобиля
     * @return список моделей автомобилей, соответстующих марке автомобиля
     * @throws ServiceException ошибка при получении списка моделей автомобилей
     */
    public List<String> takeModels(String mark) throws ServiceException {
        LOG.debug(ServiceStringConstant.SERVICE_TAKE_MODELS_STARTS_MSG);
        try {
            List<String> models = CAR_DAO.takeModels(mark);
            LOG.debug(ServiceStringConstant.SERVICE_TAKE_MODELS_ENDS_MSG);
            return models;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Получение всех типов автомобилей с DAO
     *
     * @return список всех типов автомобилей
     * @throws ServiceException ошибка при получении всех типов автомобилей
     */
    public List<CarType> takeCarTypes() throws ServiceException {
        LOG.debug(ServiceStringConstant.SERVICE_TAKE_CAR_TYPES_STARTS_MSG);
        try {
            List<CarType> carTypes = CAR_DAO.takeCarTypes();
            LOG.debug(ServiceStringConstant.SERVICE_TAKE_CAR_TYPES_ENDS_MSG);
            return carTypes;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public List<Car> takeCarsByType(String type, int pageNumber, int carsOnPage) throws ServiceException {
        LOG.debug(ServiceStringConstant.SERVICE_TAKE_CARS_BY_TYPE_START_MSG);
        int startPage = carToStartPage(pageNumber, carsOnPage);
        List<Car> cars = null;
        try {
            cars = CAR_DAO.takeCarsByType(type, startPage, carsOnPage);
            LOG.debug(ServiceStringConstant.SERVICE_TAKE_CARS_BY_TYPE_END_MSG);
            return cars;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Получение всех свободных автомобилей определенного типа в некотором временном
     * промежутке
     *
     * @param type     тип автомобиля
     * @param dateFrom дата получения автомобиля пользователем
     * @param dateTo   дата возврата автомобиля пользователем
     * @return список свободных автомобилей определенного типа в некотором временном
     * промежутке
     * @throws ServiceException ошибка при получении списка свободных автомобилей
     */
    public List<Car> takeCarsByTypeAndDate(String type, String dateFrom, String dateTo,
                                           int pageNumber, int carsOnPage) throws ServiceException {
        LOG.debug(ServiceStringConstant.SERVICE_TAKE_CARS_BY_TYPE_AND_DATE_START_MSG);
        int startPage = carToStartPage(pageNumber, carsOnPage);
        try {
            List<Car> cars = CAR_DAO.takeUnusedCarsByType(type, dateFrom, dateTo, startPage, carsOnPage);
            LOG.debug(ServiceStringConstant.SERVICE_TAKE_CARS_BY_TYPE_AND_DATE_END_MSG);
            return cars;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Получение списка всех автомобилей с DAO
     *
     * @param pageNumber
     * @param carsOnPage
     * @return список всех автомобилей
     * @throws ServiceException ошибка при получении списка автомобилей
     */
    public List<Car> takeAllCars(int pageNumber, int carsOnPage) throws ServiceException {
        LOG.debug(ServiceStringConstant.SERVICE_TAKE_ALL_CARS_STARTS_MSG);
        int startPage = carToStartPage(pageNumber, carsOnPage);
        try {
            List<Car> cars = CAR_DAO.takeAllCars(startPage, carsOnPage);
            LOG.debug(ServiceStringConstant.SERVICE_TAKE_ALL_CARS_ENDS_MSG);
            return cars;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Передача автомобиля для вставки на DAO
     *
     * @param car автомобиль для вставки
     * @throws ServiceException ошибка при вставке автомобиля
     */
    public void insertCar(Car car) throws ServiceException {
        LOG.debug(ServiceStringConstant.SERVICE_INSERT_CAR_STARTS_MSG);
        try {
            CAR_DAO.insertCar(car);
            LOG.debug(ServiceStringConstant.SERVICE_INSERT_CAR_ENDS_MSG);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Передача id автомобиля на DAO, который необходимо удалить
     *
     * @param carId id автомобиля
     * @throws ServiceException ошибка при удалении автомобиля
     */
    public void deleteCar(int carId) throws ServiceException {
        LOG.debug(ServiceStringConstant.SERVICE_DELETE_CAR_STARTS_MSG);
        try {
            CAR_DAO.deleteCarById(carId);
            LOG.debug(ServiceStringConstant.SERVICE_DELETE_CAR_ENDS_MSG);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public int countPageAmountAllCars(int amountCarsOnPage) throws ServiceException {
        LOG.debug(ServiceStringConstant.SERVICE_COUNT_PAGE_AMOUNT_ALL_CARS_STARTS_MSG);
        int pageAmount = 0;
        int carsAmount = 0;
        try {
            carsAmount = CAR_DAO.countAllCars();
            if (carsAmount%amountCarsOnPage != 0) {
                pageAmount = (carsAmount / amountCarsOnPage) + 1;
            } else {
                pageAmount = (carsAmount / amountCarsOnPage);
            }
            LOG.debug(ServiceStringConstant.SERVICE_COUNT_PAGE_AMOUNT_ALL_CARS_ENDS_MSG);
            return pageAmount;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public int countPageAmountTypeCars(String type, int amountCarsOnPage) throws ServiceException {
        LOG.debug(ServiceStringConstant.SERVICE_COUNT_PAGE_AMOUNT_TYPE_STARTS_MSG);
        int pageAmount = 0;
        int carsAmount = 0;
        try {
            carsAmount = CAR_DAO.countAllTypeCars(type);
            if (carsAmount%amountCarsOnPage != 0) {
                pageAmount = (carsAmount / amountCarsOnPage) + 1;
            } else {
                pageAmount = (carsAmount / amountCarsOnPage);
            }
            LOG.debug(ServiceStringConstant.SERVICE_COUNT_PAGE_AMOUNT_TYPE_ENDS_MSG);
            return pageAmount;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public int countPageAmountUnusedTypeCars(String type, int amountCarsOnPage, String dateFrom, String dateTo) throws ServiceException {
        LOG.debug(ServiceStringConstant.SERVICE_COUNT_PAGE_AMOUNT_UNUSED_CARS_MSG_START);
        int pageAmount = 0;
        int carsAmount = 0;
        try {
            carsAmount = CAR_DAO.countUnusedTypeCars(type, dateFrom, dateTo);
            if (carsAmount%amountCarsOnPage != 0) {
                pageAmount = (carsAmount / amountCarsOnPage) + 1;
            } else {
                pageAmount = (carsAmount / amountCarsOnPage);
            }
            LOG.debug(ServiceStringConstant.SERVICE_COUNT_PAGE_AMOUNT_UNUSED_CARS_MSG_END);
            return pageAmount;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public Car takeCarById(int id) throws ServiceException {
        LOG.debug(ServiceStringConstant.SERVICE_TAKE_CAR_BY_ID_STARTS_MSG);
        Car car = null;
        try {
            car = CAR_DAO.takeCarById(id);
            LOG.debug(ServiceStringConstant.SERVICE_TAKE_CAR_BY_ID_ENDS_MSG);
            return car;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    private int carToStartPage(int pageNumber, int carsOnPage) {
        LOG.debug(ServiceStringConstant.SERVICE_CAR_TO_START_PAGE_STARTS_MSG);
        LOG.debug(ServiceStringConstant.SERVICE_CAR_TO_START_PAGE_ENDS_MSG);
        return ((pageNumber * carsOnPage) - carsOnPage);
    }
}
