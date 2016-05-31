package by.epam.carrental.service;

import by.epam.carrental.dao.CarDAO;
import by.epam.carrental.dao.DAOFactory;
import by.epam.carrental.dao.exception.DAOException;
import by.epam.carrental.service.exception.ServiceException;

/**
 *
 * <p>
 *     Класс ValidatorUniqueCar проверяет на уникальность данные автомобиля, которые должны
 *     быть уникальными.
 * </p>
 */
public class ValidatorUniqueCar {

    /**
     * Проверка на уникальность некоторых данных автомобиля.
     * @param govNumber государстенный номер автомобиля
     * @param vin vin-код автомобиля
     * @return true - данные уникальны, false - данные не уникальны
     * @throws ServiceException ошибка в процессе извлечения модели автомобиля из
     *      базы данных
     */
    public boolean checkUnique(String govNumber, String vin) throws ServiceException{
        DAOFactory factory = DAOFactory.getInstance();
        CarDAO dao = factory.getCarDAO();
        try {
            String model = dao.findCarByGovNumberVin(govNumber, vin);
            if (model != null) {
                return false;
            } else {
                return true;
            }
        } catch (DAOException ex) {
            throw new ServiceException(ex);
        }
    }

}
