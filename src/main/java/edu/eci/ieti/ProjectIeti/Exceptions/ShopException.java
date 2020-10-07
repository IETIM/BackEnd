package edu.eci.ieti.ProjectIeti.Exceptions;

public class ShopException extends Exception {
    public static final String SHOP_NOT_FOUND = "Shop not found" ;
    public static final String PRODUCT_NOT_FOUND= "Product not found";
    public static final String SHOP_REGISTERED= "Shop already registered";
    public static final String ORDER_NOT_FOUND = "Order was not found";

    public ShopException(){
        super();
    }
    public ShopException(String message){
        super(message);
    }
}
