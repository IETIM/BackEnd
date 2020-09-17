package edu.eci.ieti.ProjectIeti.controllers;


import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionTienda;
import edu.eci.ieti.ProjectIeti.model.Tienda;
import edu.eci.ieti.ProjectIeti.model.TipoComercio;
import edu.eci.ieti.ProjectIeti.services.TiendaServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="*")
@RequestMapping(value = "/tiendas")
public class TiendaController {
    @Autowired
    private TiendaServices tiendaServices;



    @GetMapping(path = "/{nombre}")
    public ResponseEntity<?> getTiendaByNombre(@PathVariable String nombre){
        try {
            return new ResponseEntity<>(tiendaServices.getTiendaByNombre(nombre), HttpStatus.ACCEPTED);
        } catch (ExceptionTienda e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping()
    public ResponseEntity<?> getTiendas(@RequestParam(name = "tipo") Optional<TipoComercio> tipoComercio){
        List<Tienda>tiendas;
        if(tipoComercio.isPresent()){
            tiendas=tiendaServices.getTiendasByTipo(tipoComercio.get());
        }
        else {
            tiendas=tiendaServices.getTiendas();
        }
        return new ResponseEntity<>(tiendas, HttpStatus.ACCEPTED);
    }
}
