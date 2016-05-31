package by.epam.carrental.listener;

import by.epam.carrental.dao.connectionpoolhelper.exception.ConnectionPoolException;
import by.epam.carrental.dao.impl.ConnectionPooldb;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ProjectServletContextListener implements ServletContextListener{

    private static final Logger LOG = LogManager.getLogger(ProjectServletContextListener.class.getName());

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        try {
            ConnectionPooldb pool = ConnectionPooldb.getInstance();
            pool.dispose();
            LOG.debug("ServletContextListener : Connection Pool has been destroyed");
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
            LOG.debug("ServletContextListener : Connection Pool has been initialized");
        } catch (ConnectionPoolException ex) {
            LOG.error(ex);
            throw new RuntimeException(ex);
        }
    }

}
