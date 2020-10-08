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
    private ArrayList<Purchase> purchases;
    private double total;
    private String currency;
    private String method;
    private String intent;
    private String description;
    private String store;
    private String state;

    public Order(String store,double total, String currency, String method, String intent, String description,List<Purchase> purchases) {
        this.store = store;
        this.currency = currency;
        this.method = method;
        this.intent = intent;
        this.description = description;
        this.state = "not payed";
        this.total = total;
        this.purchases = (ArrayList<Purchase>) purchases;
    }

    public Order() {
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
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

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(ArrayList<Purchase> purchases) {
        this.purchases = purchases;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}