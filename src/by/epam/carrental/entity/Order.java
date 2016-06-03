package by.epam.carrental.entity;

import java.io.Serializable;

public class Order implements Serializable {

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

    public Order() {

    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (carId != order.carId) return false;
        if (Double.compare(order.damagePrice, damagePrice) != 0) return false;
        if (id != order.id) return false;
        if (Double.compare(order.orderPrice, orderPrice) != 0) return false;
        if (userId != order.userId) return false;
        if (car != null ? !car.equals(order.car) : order.car != null) return false;
        if (carGovNumber != null ? !carGovNumber.equals(order.carGovNumber) : order.carGovNumber != null) return false;
        if (carMark != null ? !carMark.equals(order.carMark) : order.carMark != null) return false;
        if (carModel != null ? !carModel.equals(order.carModel) : order.carModel != null) return false;
        if (info != null ? !info.equals(order.info) : order.info != null) return false;
        if (realDateFrom != null ? !realDateFrom.equals(order.realDateFrom) : order.realDateFrom != null) return false;
        if (realDateTo != null ? !realDateTo.equals(order.realDateTo) : order.realDateTo != null) return false;
        if (returnPlace != null ? !returnPlace.equals(order.returnPlace) : order.returnPlace != null) return false;
        if (shippingPlace != null ? !shippingPlace.equals(order.shippingPlace) : order.shippingPlace != null)
            return false;
        if (status != null ? !status.equals(order.status) : order.status != null) return false;
        if (supposedDateFrom != null ? !supposedDateFrom.equals(order.supposedDateFrom) : order.supposedDateFrom != null)
            return false;
        if (supposedDateTo != null ? !supposedDateTo.equals(order.supposedDateTo) : order.supposedDateTo != null)
            return false;
        if (user != null ? !user.equals(order.user) : order.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + userId;
        result = 31 * result + carId;
        result = 31 * result + (supposedDateFrom != null ? supposedDateFrom.hashCode() : 0);
        result = 31 * result + (supposedDateTo != null ? supposedDateTo.hashCode() : 0);
        result = 31 * result + (realDateFrom != null ? realDateFrom.hashCode() : 0);
        result = 31 * result + (realDateTo != null ? realDateTo.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (shippingPlace != null ? shippingPlace.hashCode() : 0);
        result = 31 * result + (returnPlace != null ? returnPlace.hashCode() : 0);
        temp = Double.doubleToLongBits(orderPrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(damagePrice);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (info != null ? info.hashCode() : 0);
        result = 31 * result + (carMark != null ? carMark.hashCode() : 0);
        result = 31 * result + (carModel != null ? carModel.hashCode() : 0);
        result = 31 * result + (carGovNumber != null ? carGovNumber.hashCode() : 0);
        result = 31 * result + (car != null ? car.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
