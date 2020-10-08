package edu.eci.ieti.ProjectIeti.model;

import org.springframework.data.annotation.Id;

public class Purchase {
    @Id
    private String id;
    private String productId;
    private int quantity;

    public Purchase() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
