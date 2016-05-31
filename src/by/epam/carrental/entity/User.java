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
        if (!address.equals(user.address)) return false;
        if (!eMail.equals(user.eMail)) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!lastName.equals(user.lastName)) return false;
        if (!login.equals(user.login)) return false;
        if (!middleName.equals(user.middleName)) return false;
        if (!passport.equals(user.passport)) return false;
        if (!phone.equals(user.phone)) return false;

        return true;
    }

}
