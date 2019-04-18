package com.k.menu.model;

/**
 * Created by Karthik on 15-01-2018.
 */

public class Order {
    private String Productid;
    private String ProductName;
    private String Quantity;
    private String Price;

    public Order() {
    }

    public Order(String productid, String productName, String quantity, String price) {
        Productid = productid;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
    }

    public String getProductid() {
        return Productid;
    }

    public void setProductid(String productid) {
        Productid = productid;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }
}
