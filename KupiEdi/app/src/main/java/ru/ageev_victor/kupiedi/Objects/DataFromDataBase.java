package ru.ageev_victor.kupiedi.Objects;

public class DataFromDataBase {

    private String productName;
    private float productCount;

    DataFromDataBase(String productName, float productCount) {
        this.productCount = productCount;
        this.productName = productName;
    }

    public float getProductCount() {
        return productCount;
    }

    public String getProductName() {
        return productName;
    }
}
