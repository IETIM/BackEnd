package edu.eci.ieti.ProjectIeti.services;

import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionTienda;
import edu.eci.ieti.ProjectIeti.model.Producto;
import edu.eci.ieti.ProjectIeti.model.Tienda;
import edu.eci.ieti.ProjectIeti.model.TipoComercio;

import java.util.List;

public interface TiendaServices {

    List<Tienda>getTiendas();

    List<Tienda>getTiendasByTipo(TipoComercio tipoComercio);

    Tienda getTiendaByNombre(String nombre) throws ExceptionTienda;

}

