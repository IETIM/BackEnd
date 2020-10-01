package edu.eci.ieti.ProjectIeti.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "storekeepers")
@TypeAlias("storekeeper")
public class Storekeeper{

    @Id
    private String id;

    @Indexed(unique = true)
    private String email;

    private String name;

    private String password;

    private int cellphone;

    @DBRef
    private List<Order> orders;

    @DBRef
    private List<Role> authorities;

    public Storekeeper() {
        this.authorities= new ArrayList<>();
    }

    public Storekeeper(String id, String email, String name, String password, int cellphone, List<Shop> shops) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.cellphone = cellphone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCellphone() {
        return cellphone;
    }

    public void setCellphone(int cellphone) {
        this.cellphone = cellphone;
    }


}
