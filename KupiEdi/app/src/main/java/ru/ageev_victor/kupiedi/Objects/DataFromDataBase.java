package ru.ageev_victor.kupiedi.Objects;


public class DataFromDataBase {

    String productName;
    int productCount;

    public DataFromDataBase(String productName, int productCount) {
        this.productCount = productCount;
        this.productName = productName;
    }

    public int getProductCount() {
        return productCount;
    }

    public String getProductName() {
        return productName;
    }
}
