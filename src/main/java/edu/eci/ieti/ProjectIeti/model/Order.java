package edu.eci.ieti.ProjectIeti.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "orders")
@TypeAlias("order")
public class Order {
    @Id
    private String id;

    private double price;
    private String currency;
    private String method;
    private String intent;
    private String description;
    private String shop;
    private List<Product> products;

    public Order(String shop,double price, String currency, String method, String intent, String description,List<Product> products) {
        this.shop = shop;
        this.currency = currency;
        this.method = method;
        this.intent = intent;
        this.description = description;
        this.products = products;
    }

    public Order() {
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}