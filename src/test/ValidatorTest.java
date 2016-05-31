import by.epam.carrental.service.Validator;
import org.junit.Assert;
import org.junit.Test;

public class ValidatorTest {

    @Test
    public void validateLoginTest() {
        Validator validator = Validator.getInstance();

        boolean validLogin = validator.validateLogin("anton");
        Assert.assertTrue(validLogin);

        validLogin = validator.validateLogin("@nton");
        Assert.assertFalse(validLogin);

        validLogin = validator.validateLogin("antonantonantonantonanotonnn");
        Assert.assertFalse(validLogin);
    }

    @Test
    public void validatePasswordTest() {
        Validator validator = Validator.getInstance();

        boolean validPassword = validator.validatePassword("");
        Assert.assertFalse(validPassword);

        validPassword = validator.validatePassword("1qaz@WSX");
        Assert.assertTrue(validPassword);
    }
}

