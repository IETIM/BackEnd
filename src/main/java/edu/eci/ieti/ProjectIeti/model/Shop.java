package edu.eci.ieti.ProjectIeti.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "shops")
@TypeAlias("shop")
public class Shop {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    @DBRef
    private List<Product> products;

    @DBRef
    private Storekeeper owner;

    private String ubication;

    private String  type;

    private String apiClient;

    private String apiSecret;

    public Shop(String name, List<Product> products, Storekeeper owner, String ubication, String type) {
        this.name = name;
        this.products = products;
        this.owner = owner;
        this.ubication = ubication;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getUbication() {
        return ubication;
    }

    public void setUbication(String ubication) {
        this.ubication = ubication;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApiClient() {
        return apiClient;
    }

    public void setApiClient(String apiClient) {
        this.apiClient = apiClient;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    public Storekeeper getOwner() {
        return owner;
    }

    public void setOwner(Storekeeper owner) {
        this.owner = owner;
    }
}
