package edu.eci.ieti.ProjectIeti.services;

import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionTienda;
import edu.eci.ieti.ProjectIeti.model.Producto;

import java.util.List;

public interface ProductServices {

    List<Producto> getProducts(String tienda) throws ExceptionTienda;
}
