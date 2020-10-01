package edu.eci.ieti.ProjectIeti.services;

import edu.eci.ieti.ProjectIeti.Exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.model.Product;

import java.util.List;

public interface ProductServices {

    List<Product> getProducts(String idShop) throws ShopException;

    void addProduct(Product product, String idShop) throws ShopException;

    void updateProduct(Product product, String idProduct)throws ShopException;

    void deleteProduct(String idProduct)throws ShopException;
}
