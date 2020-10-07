package edu.eci.ieti.ProjectIeti.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "orders")
@TypeAlias("order")
public class Order {
    @Id
    private String id;
    @DBRef
    private ArrayList<Product> products;
    private double price;
    private String currency;
    private String method;
    private String intent;
    private String description;
    private String shop;
    private String state;

    public Order(String shop,double price, String currency, String method, String intent, String description,List<Product> products) {
        this.shop = shop;
        this.currency = currency;
        this.method = method;
        this.intent = intent;
        this.description = description;
        this.state = "not payed";
    }

    public Order(String shop, ArrayList<Product> products, String method, String description,String currency){
        this.shop= shop;
        this.products=products;
        this.method=method;
        this.description=description;
        this.intent = "SALE";
        this.price = calculatePrice(products);
        this.currency = currency;
        this.state = "not payed";
    }

    private double calculatePrice(ArrayList<Product> products) {
        double total = 0;
        for (Product p: products){
            total+=p.getPrice();
        }
        return total;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}