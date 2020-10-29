package edu.eci.ieti.ProjectIeti.services;

import edu.eci.ieti.ProjectIeti.exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.model.Product;

import java.util.List;

public interface ProductServices {

    List<Product> getProducts(String idShop) throws ShopException;

    Product addProduct(Product product, String idShop) throws ShopException;

    Product updateProduct(Product product, String idProduct)throws ShopException;

    void deleteProduct(String idProduct)throws ShopException;
}
