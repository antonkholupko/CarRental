package by.epam.carrental;

import by.epam.carrental.dao.impl.UserDAOImplTest;
import by.epam.carrental.service.OrderServiceTest;
import by.epam.carrental.service.ValidatorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Suite.SuiteClasses({UserDAOImplTest.class, OrderServiceTest.class, ValidatorTest.class})
@RunWith(Suite.class)
public class TestSuite {
}
