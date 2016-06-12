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

    private static final CarDAO carDao = DAOFactory.getInstance().getCarDAO();

    private static final String TAKE_MARKS_MSG = "CarService : takeMarks";
    private static final String TAKE_MODELS_MSG = "CarService : takeModels";
    private static final String TAKE_CAR_TYPES_MSG = "CarService : takeCarTypes";
    private static final String TAKE_CARS_BY_TYPE_START_MSG = "CarService : takeCarsByType : starts";
    private static final String TAKE_CARS_BY_TYPE_END_MSG = "CarService : takeCarsByType : ends";
    private static final String TAKE_CARS_BY_TYPE_AND_DATE_START_MSG = "CarService : takeCarsByTypeAndDate : starts";
    private static final String TAKE_CARS_BY_TYPE_AND_DATE_END_MSG = "CarService : takeCarsByTypeAndDate : ends";
    private static final String TAKE_UNUSED_CARS_MSG = "CarService : takeUnusedCars";
    private static final String TAKE_ALL_CARS_MSG = "CarService : takeAllCars";
    private static final String INSERT_CAR_MSG = "CarService : insertCar";
    private static final String DELETE_CAR_MSG = "CarService : deleteCar";
    private static final String COUNT_PAGE_AMOUNT_ALL_CARS_MSG = "CarService : countPageAmountAllCars";
    private static final String COUNT_PAGE_AMOUNT_TYPE_MSG = "CarService : countPageAmountTypeCars";
    private static final String COUNT_PAGE_AMOUNT_UNUSED_CARS_MSG_START = "CarService : countPageAmountUnusedTypeCars : starts";
    private static final String COUNT_PAGE_AMOUNT_UNUSED_CARS_MSG_END = "CarService : countPageAmountUnusedTypeCars : ends";
    private static final String CAR_TO_START_PAGE = "CarService : carToStartPage";
    private static final String TAKE_CAR_BY_ID_MSG = "CarService : takeCarById";

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
        LOG.debug(TAKE_MARKS_MSG);
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
     *
     * @param mark марка автомобиля
     * @return список моделей автомобилей, соответстующих марке автомобиля
     * @throws ServiceException ошибка при получении списка моделей автомобилей
     */
    public List<String> takeModels(String mark) throws ServiceException {
        LOG.debug(TAKE_MODELS_MSG);
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
     *
     * @return список всех типов автомобилей
     * @throws ServiceException ошибка при получении всех типов автомобилей
     */
    public List<CarType> takeCarTypes() throws ServiceException {
        LOG.debug(TAKE_CAR_TYPES_MSG);
        try {
            DAOFactory factory = DAOFactory.getInstance();
            CarDAO dao = factory.getCarDAO();
            List<CarType> carTypes = dao.takeCarTypes();
            return carTypes;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public List<Car> takeCarsByType(String type, int pageNumber, int carsOnPage) throws ServiceException {
        LOG.debug(TAKE_CARS_BY_TYPE_START_MSG);
        DAOFactory factory = DAOFactory.getInstance();
        CarDAO dao = factory.getCarDAO();
        int startPage = carToStartPage(pageNumber, carsOnPage);
        List<Car> cars = null;
        try {
            cars = dao.takeCarsByType(type, startPage, carsOnPage);
            LOG.debug(TAKE_CARS_BY_TYPE_END_MSG);
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
        LOG.debug(TAKE_CARS_BY_TYPE_AND_DATE_START_MSG);
        DAOFactory factory = DAOFactory.getInstance();
        int startPage = carToStartPage(pageNumber, carsOnPage);
        CarDAO dao = factory.getCarDAO();
        try {
            List<Car> cars = dao.takeUnusedCarsByType(type, dateFrom, dateTo, startPage, carsOnPage);
            LOG.debug(TAKE_CARS_BY_TYPE_AND_DATE_END_MSG);
            return cars;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    /**
     * Получение всех свободных автомобилей в определенном временном промежутке с DAO
     *
     * @param supposedDateFrom дата получения автомобиля пользователем
     * @param supposedDateTo   дата возврата автомобиля пользователем
     * @return список свободных автомобилей в определенном временном промежутке
     * @throws ServiceException ошибка при получении списка автомобилей
     */
    public List<Car> takeUnusedCars(String supposedDateFrom, String supposedDateTo,
                                    int pageNumber, int carsOnPage) throws ServiceException {
        LOG.debug(TAKE_UNUSED_CARS_MSG);
        DAOFactory factory = DAOFactory.getInstance();
        int startPage = carToStartPage(pageNumber, carsOnPage);
        CarDAO dao = factory.getCarDAO();
        try {
            List<Car> cars = dao.takeUnusedCars(supposedDateFrom, supposedDateTo, startPage, carsOnPage);
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
        LOG.debug(TAKE_ALL_CARS_MSG);
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
     *
     * @param car автомобиль для вставки
     * @throws ServiceException ошибка при вставке автомобиля
     */
    public void insertCar(Car car) throws ServiceException {
        LOG.debug(INSERT_CAR_MSG);
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
     *
     * @param carId id автомобиля
     * @throws ServiceException ошибка при удалении автомобиля
     */
    public void deleteCar(int carId) throws ServiceException {
        LOG.debug(DELETE_CAR_MSG);
        DAOFactory factory = DAOFactory.getInstance();
        CarDAO dao = factory.getCarDAO();
        try {
            dao.deleteCarById(carId);
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public int countPageAmountAllCars(int amountCarsOnPage) throws ServiceException {
        LOG.debug(COUNT_PAGE_AMOUNT_ALL_CARS_MSG);
        int pageAmount = 0;
        int carsAmount = 0;
        DAOFactory factory = DAOFactory.getInstance();
        CarDAO dao = factory.getCarDAO();
        try {
            carsAmount = dao.countAllCars();
            if (carsAmount%amountCarsOnPage != 0) {
                pageAmount = (carsAmount / amountCarsOnPage) + 1;
            } else {
                pageAmount = (carsAmount / amountCarsOnPage);
            }
            return pageAmount;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public int countPageAmountTypeCars(String type, int amountCarsOnPage) throws ServiceException {
        LOG.debug(COUNT_PAGE_AMOUNT_TYPE_MSG);
        int pageAmount = 0;
        int carsAmount = 0;
        DAOFactory factory = DAOFactory.getInstance();
        CarDAO dao = factory.getCarDAO();
        try {
            carsAmount = dao.countAllTypeCars(type);
            if (carsAmount%amountCarsOnPage != 0) {
                pageAmount = (carsAmount / amountCarsOnPage) + 1;
            } else {
                pageAmount = (carsAmount / amountCarsOnPage);
            }
            return pageAmount;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public int countPageAmountUnusedTypeCars(String type, int amountCarsOnPage, String dateFrom, String dateTo) throws ServiceException {
        LOG.debug(COUNT_PAGE_AMOUNT_UNUSED_CARS_MSG_START);
        int pageAmount = 0;
        int carsAmount = 0;
        DAOFactory factory = DAOFactory.getInstance();
        CarDAO dao = factory.getCarDAO();
        try {
            carsAmount = dao.countUnusedTypeCars(type, dateFrom, dateTo);
            if (carsAmount%amountCarsOnPage != 0) {
                pageAmount = (carsAmount / amountCarsOnPage) + 1;
            } else {
                pageAmount = (carsAmount / amountCarsOnPage);
            }
            LOG.debug(COUNT_PAGE_AMOUNT_UNUSED_CARS_MSG_END);
            return pageAmount;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    public Car takeCarById(int id) throws ServiceException {
        LOG.debug(TAKE_CAR_BY_ID_MSG);
        DAOFactory factory = DAOFactory.getInstance();
        CarDAO dao = factory.getCarDAO();
        Car car = null;
        try {
            car = dao.takeCarById(id);
            return car;
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

    private int carToStartPage(int pageNumber, int carsOnPage) {
        LOG.debug(CAR_TO_START_PAGE);
        return ((pageNumber * carsOnPage) - carsOnPage);
    }
}
