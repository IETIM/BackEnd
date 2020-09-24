package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionTienda;
import edu.eci.ieti.ProjectIeti.model.Producto;
import edu.eci.ieti.ProjectIeti.model.Tienda;
import edu.eci.ieti.ProjectIeti.persistence.ProductoRepository;
import edu.eci.ieti.ProjectIeti.persistence.TiendaRepository;
import edu.eci.ieti.ProjectIeti.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServicesImpl implements ProductServices {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private TiendaRepository tiendaRepository;


    @Override
    public List<Producto> getProducts(Long idTienda) throws ExceptionTienda {
        Optional<Tienda> findTienda =tiendaRepository.findById(idTienda);
        Tienda tienda=findTienda.orElseThrow(() -> new ExceptionTienda(ExceptionTienda.TIENDA_NO_ENCONTRADA));
        return tienda.getProductos();
    }

    @Override
    public void addProduct(Producto producto, Long idTienda) throws ExceptionTienda {
        Optional<Tienda> findTienda =tiendaRepository.findById(idTienda);
        Tienda tienda=findTienda.orElseThrow(() -> new ExceptionTienda(ExceptionTienda.TIENDA_NO_ENCONTRADA));
        tienda.getProductos().add(producto);
        tiendaRepository.save(tienda);
    }

    @Override
    public void updateProduct(Producto producto, Long idProducto) throws ExceptionTienda {
        Optional<Producto> optionalProducto= productoRepository.findById(idProducto);
        Producto productoActual= optionalProducto.orElseThrow(() -> new ExceptionTienda(ExceptionTienda.PRODUCTO_NO_ENCONTRADO));
        productoActual.setDescripcion(producto.getDescripcion());
        productoActual.setExistencias(producto.getExistencias());
        productoActual.setNombre(producto.getNombre());
        producto.setPrecio(producto.getPrecio());
        productoRepository.save(productoActual);
    }

    @Override
    public void deleteProduct(Long idProducto) throws ExceptionTienda {
        productoRepository.deleteById(idProducto);
    }
}
