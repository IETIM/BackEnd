package edu.eci.ieti.ProjectIeti.services;

import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionShop;
import edu.eci.ieti.ProjectIeti.model.Product;

import java.util.List;

public interface ProductServices {

    List<Product> getProducts(String idShop) throws ExceptionShop;

    void addProduct(Product product, String idShop) throws ExceptionShop;

    void updateProduct(Product product, String idProduct)throws ExceptionShop;

    void deleteProduct(String idProduct)throws ExceptionShop;
}
