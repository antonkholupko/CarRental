package by.epam.carrental.entity;

public class User {

    private int id;
    private String login;
    private int hashPassword;
    private String type;
    private String eMail;
    private String lastName;
    private String firstName;
    private String middleName;
    private String phone;
    private String passport;
    private String address;

    public User() {

    }

    public User(String login, int hashPassword, String lastName, String firstName, String middleName, String email,
            String phone, String passport, String address) {
        this.login = login;
        this.hashPassword = hashPassword;
        this.eMail = email;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.phone = phone;
        this.passport = passport;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(int hashPassword) {
        this.hashPassword = hashPassword;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (hashPassword != user.hashPassword) return false;
        if (id != user.id) return false;
        if (address != null ? !address.equals(user.address) : user.address != null) return false;
        if (eMail != null ? !eMail.equals(user.eMail) : user.eMail != null) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (login != null ? !login.equals(user.login) : user.login != null) return false;
        if (middleName != null ? !middleName.equals(user.middleName) : user.middleName != null) return false;
        if (passport != null ? !passport.equals(user.passport) : user.passport != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        if (type != null ? !type.equals(user.type) : user.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + hashPassword;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (eMail != null ? eMail.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (passport != null ? passport.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
