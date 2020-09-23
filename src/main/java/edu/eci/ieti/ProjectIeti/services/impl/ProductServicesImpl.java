package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionTienda;
import edu.eci.ieti.ProjectIeti.model.Producto;
import edu.eci.ieti.ProjectIeti.persistence.ProductoRepository;
import edu.eci.ieti.ProjectIeti.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServicesImpl implements ProductServices {

    @Autowired
    private ProductoRepository productoRepository;


    @Override
    public List<Producto> getProducts(String tienda) throws ExceptionTienda {
        return productoRepository.findAllByTienda(tienda);
    }
}
