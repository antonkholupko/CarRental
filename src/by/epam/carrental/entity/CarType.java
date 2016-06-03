package by.epam.carrental.entity;

public class CarType {

    String type;
    double price;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CarType carType = (CarType) o;

        if (Double.compare(carType.price, price) != 0) return false;
        if (type != null ? !type.equals(carType.type) : carType.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = type != null ? type.hashCode() : 0;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
