package by.epam.carrental.dao;

import by.epam.carrental.dao.impl.CarDAOImpl;
import by.epam.carrental.dao.impl.OrderDAOImpl;
import by.epam.carrental.dao.impl.UserDAOImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class DAOFactory {

    private static final Logger LOG = LogManager.getLogger(DAOFactory.class.getName());
    private static final DAOFactory instance = new DAOFactory();
    private static final String INIT_DAO_FACTORY_MSG = "DAOFactory : init DAO factory";

    private static final UserDAO USER_DAO = new UserDAOImpl();
    private static final CarDAO CAR_DAO = new CarDAOImpl();
    private static final OrderDAO ORDER_DAO = new OrderDAOImpl();

    private DAOFactory() {
        LOG.debug(INIT_DAO_FACTORY_MSG);
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return USER_DAO;
    }

    public CarDAO getCarDAO() {
        return CAR_DAO;
    }

    public OrderDAO getOrderDAO() {
        return ORDER_DAO;
    }
}
