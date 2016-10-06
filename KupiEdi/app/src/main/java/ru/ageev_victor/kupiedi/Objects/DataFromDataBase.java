package ru.ageev_victor.kupiedi.Objects;

public class DataFromDataBase {

    private String productName;
    private double productCount;

    DataFromDataBase(String productName, double productCount) {
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
