package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionTienda;
import edu.eci.ieti.ProjectIeti.model.Tienda;
import edu.eci.ieti.ProjectIeti.model.TipoComercio;
import edu.eci.ieti.ProjectIeti.persistence.TiendaRepository;
import edu.eci.ieti.ProjectIeti.services.TiendaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TiendaServicesImpl implements TiendaServices {

    @Autowired
    private TiendaRepository tiendaRepository;

    @Override
    public List<Tienda> getTiendas() {
        return tiendaRepository.findAll();
    }

    @Override
    public List<Tienda> getTiendasByTipo(TipoComercio tipoComercio) {
        return tiendaRepository.findAllByTipoComercio(tipoComercio);
    }

    @Override
    public Tienda getTiendaByNombre(String nombre) throws ExceptionTienda {
        return tiendaRepository.findByNombre(nombre).orElseThrow(() -> new ExceptionTienda(ExceptionTienda.TIENDA_NO_ENCONTRADA));
    }
}
