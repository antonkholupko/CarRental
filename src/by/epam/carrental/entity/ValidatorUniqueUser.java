package by.epam.carrental.entity;

/**
 *
 * <p>
 *     Класс ValidatorUniqueUser проверяет на уникальность данные пользователя, которые
 *     должны быть уникальны.
 * </p>
 */
public class ValidatorUniqueUser {

    private boolean uniqueLogin;
    private boolean uniqueEmail;
    private boolean uniquePassport;

    public ValidatorUniqueUser() {
        uniqueLogin = false;
        uniqueEmail = false;
        uniquePassport = false;
    }

    public boolean isUniqueLogin() {
        return uniqueLogin;
    }

    public void setUniqueLogin(boolean uniqueLogin) {
        this.uniqueLogin = uniqueLogin;
    }

    public boolean isUniqueEmail() {
        return uniqueEmail;
    }

    public void setUniqueEmail(boolean uniqueEmail) {
        this.uniqueEmail = uniqueEmail;
    }

    public boolean isUniquePassport() {
        return uniquePassport;
    }

    public void setUniquePassport(boolean uniquePassport) {
        this.uniquePassport = uniquePassport;
    }


}
