package by.epam.carrental.dao.impl;

import by.epam.carrental.dao.DAOFactory;
import by.epam.carrental.dao.UserDAO;
import by.epam.carrental.dao.connectionpoolhelper.exception.ConnectionPoolException;
import by.epam.carrental.dao.exception.DAOException;
import by.epam.carrental.entity.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserDAOImplTest {

    private static ConnectionPooldb connectionPool;
    private static UserDAO userDAO;

    @BeforeClass
    public static void init() throws ConnectionPoolException {
        connectionPool = ConnectionPooldb.getInstance();
        connectionPool.initPoolData();
        userDAO = DAOFactory.getInstance().getUserDAO();
    }

    @AfterClass
    public static void dispose() throws ConnectionPoolException {
        connectionPool.dispose();
    }

    @Test
    public void testFindUser() throws Exception {
        String login = "anton";
        int hashPswd = "qwe".hashCode();
        String lastName = "Холупко";
        String firstName = "Антон";
        User user = userDAO.findUser(login, hashPswd);
        assertEquals(lastName, user.getLastName());
        assertEquals(login, user.getLogin());
        assertEquals(firstName, user.getFirstName());

        hashPswd = "qwgt".hashCode();
        user = userDAO.findUser(login, hashPswd);
        assertNull(user);
    }

    @Test(expected = DAOException.class)
    public void testAddUser() throws Exception {
        User user = new User();
        user.setLogin("test");
        user.setHashPassword(12254885);
        user.setLastName("Test");
        user.setFirstName("Test");
        user.setMiddleName("Test");
        userDAO.addUser(user);
    }

    @Test
    public void testFindUserById() throws Exception {
        int id = 17;
        User user = userDAO.findUserById(id);
        assertEquals("anton", user.getLogin());
    }
}