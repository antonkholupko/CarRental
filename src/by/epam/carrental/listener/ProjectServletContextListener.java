package by.epam.carrental.listener;

import by.epam.carrental.dao.connectionpoolhelper.exception.ConnectionPoolException;
import by.epam.carrental.dao.impl.ConnectionPooldb;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ProjectServletContextListener implements ServletContextListener{

    private static final Logger LOG = LogManager.getLogger(ProjectServletContextListener.class.getName());
    private static final String DESTROY_MSG = "ServletContextListener : Connection Pool has been destroyed";
    private static final String INIT_MSG = "ServletContextListener : Connection Pool has been initialized";

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        try {
            ConnectionPooldb pool = ConnectionPooldb.getInstance();
            pool.dispose();
            LOG.debug(DESTROY_MSG);
        } catch (ConnectionPoolException ex) {
            LOG.error(ex);
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        try {
            ConnectionPooldb pool = ConnectionPooldb.getInstance();
            pool.initPoolData();
            LOG.debug(INIT_MSG);
        } catch (ConnectionPoolException ex) {
            LOG.error(ex);
            throw new RuntimeException(ex);
        }
    }

}
