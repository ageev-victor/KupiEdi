package ru.ageev_victor.kupiedi.Objects;


public class DataFromDataBase {

    String productName;
    double productCount;

    public DataFromDataBase(String productName, double productCount) {
        this.productCount = productCount;
        this.productName = productName;
    }

    public double getProductCount() {
        return productCount;
    }

    public String getProductName() {
        return productName;
    }
}
