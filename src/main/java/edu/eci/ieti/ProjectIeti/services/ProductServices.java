package edu.eci.ieti.ProjectIeti.services;

import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionTienda;
import edu.eci.ieti.ProjectIeti.model.Producto;

import java.util.List;

public interface ProductServices {

    List<Producto> getProducts(Long idTienda) throws ExceptionTienda;

    void addProduct(Producto producto, Long idTienda) throws ExceptionTienda;

    void updateProduct(Producto producto, Long idProducto)throws ExceptionTienda;

    void deleteProduct(Long idProducto)throws ExceptionTienda;
}
