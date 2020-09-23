package edu.eci.ieti.ProjectIeti.controllers;

import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionTienda;
import edu.eci.ieti.ProjectIeti.model.Producto;
import edu.eci.ieti.ProjectIeti.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @GetMapping(path = "/{tienda}")
    public ResponseEntity<?> getProductsByTienda(@PathVariable String tienda){
        try {
            return ResponseEntity.ok(productServices.getProducts(tienda));
        } catch (ExceptionTienda e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/{tienda}")
    public ResponseEntity<?> addProduct(@RequestBody Producto producto){
        System.out.println("NO ENTRES!!!");
        return null;
    }
}
