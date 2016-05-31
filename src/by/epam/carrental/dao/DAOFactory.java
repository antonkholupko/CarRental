package by.epam.carrental.dao;

import by.epam.carrental.dao.impl.CarDAOdb;
import by.epam.carrental.dao.impl.OrderDAOdb;
import by.epam.carrental.dao.impl.UserDAOdb;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class DAOFactory {

    private static final Logger LOG = LogManager.getLogger(DAOFactory.class.getName());
    private static final DAOFactory instance = new DAOFactory();

    private DAOFactory() {
        LOG.debug("DAOFactory : init DAO factory");
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return new UserDAOdb();
    }

    public CarDAO getCarDAO() {return new CarDAOdb();}

    public OrderDAO getOrderDAO() {return new OrderDAOdb();}
}
