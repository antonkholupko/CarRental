package by.epam.carrental.entity;

public class Order {

    private int id;
    private int userId;
    private int carId;
    private String supposedDateFrom;
    private String supposedDateTo;
    private String realDateFrom;
    private String realDateTo;
    private String status;
    private String shippingPlace;
    private String returnPlace;
    private double orderPrice;
    private double damagePrice;
    private String info;
    private String carMark;
    private String carModel;
    private String carGovNumber;
    private Car car;
    private User user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getSupposedDateFrom() {
        return supposedDateFrom;
    }

    public void setSupposedDateFrom(String supposedDateFrom) {
        this.supposedDateFrom = supposedDateFrom;
    }

    public String getSupposedDateTo() {
        return supposedDateTo;
    }

    public void setSupposedDateTo(String supposedDateTo) {
        this.supposedDateTo = supposedDateTo;
    }

    public String getRealDateFrom() {
        return realDateFrom;
    }

    public void setRealDateFrom(String realDateFrom) {
        this.realDateFrom = realDateFrom;
    }

    public String getRealDateTo() {
        return realDateTo;
    }

    public void setRealDateTo(String realDateTo) {
        this.realDateTo = realDateTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getShippingPlace() {
        return shippingPlace;
    }

    public void setShippingPlace(String shippingPlace) {
        this.shippingPlace = shippingPlace;
    }

    public String getReturnPlace() {
        return returnPlace;
    }

    public void setReturnPlace(String returnPlace) {
        this.returnPlace = returnPlace;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public double getDamagePrice() {
        return damagePrice;
    }

    public void setDamagePrice(double damagePrice) {
        this.damagePrice = damagePrice;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getCarMark() {
        return carMark;
    }

    public void setCarMark(String carMark) {
        this.carMark = carMark;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarGovNumber() {
        return carGovNumber;
    }

    public void setCarGovNumber(String carGovNumber) {
        this.carGovNumber = carGovNumber;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
