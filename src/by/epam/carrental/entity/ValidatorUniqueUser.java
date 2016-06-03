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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValidatorUniqueUser that = (ValidatorUniqueUser) o;

        if (uniqueEmail != that.uniqueEmail) return false;
        if (uniqueLogin != that.uniqueLogin) return false;
        if (uniquePassport != that.uniquePassport) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (uniqueLogin ? 1 : 0);
        result = 31 * result + (uniqueEmail ? 1 : 0);
        result = 31 * result + (uniquePassport ? 1 : 0);
        return result;
    }
}
