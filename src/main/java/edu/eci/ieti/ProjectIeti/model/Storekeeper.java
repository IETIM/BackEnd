package edu.eci.ieti.ProjectIeti.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "storekeepers")
@TypeAlias("storekeeper")
public class Storekeeper{

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    @DBRef
    private Shop shop;

    public Storekeeper() {
    }

    public Storekeeper( String email, Shop shop) {
        this.email = email;
        this.shop = shop;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}

