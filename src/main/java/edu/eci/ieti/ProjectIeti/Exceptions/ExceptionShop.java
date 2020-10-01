package edu.eci.ieti.ProjectIeti.Exceptions;

public class ExceptionShop extends Exception {
    public static final String SHOP_NOT_FOUND = "Shop not found" ;
    public static final String PRODUCT_NOT_FOUND= "Product not found";
    public static final String SHOP_REGISTERED= "Shop already registered";

    public ExceptionShop(){
        super();
    }
    public ExceptionShop(String message){
        super(message);
    }
}
