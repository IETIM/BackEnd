package edu.eci.ieti.ProjectIeti.controllers;

import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionTienda;
import edu.eci.ieti.ProjectIeti.model.Producto;
import edu.eci.ieti.ProjectIeti.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/tiendas")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @GetMapping(path = "/{idTienda}/products")
    public ResponseEntity<?> getProductsByTienda(@PathVariable Long idTienda){
        try {
            return ResponseEntity.ok(productServices.getProducts(idTienda));
        } catch (ExceptionTienda e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/{idTienda}/products")
    public ResponseEntity<?> addProduct(@RequestBody Producto producto,@PathVariable Long idTienda){
        try {
            productServices.addProduct(producto,idTienda);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (ExceptionTienda e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(value = "/products/{idProduct}")
    public ResponseEntity<?> updateProduct(@RequestBody Producto producto,@PathVariable Long idProducto){
        try{
            productServices.updateProduct(producto,idProducto);
            return new ResponseEntity<>(HttpStatus.OK);


        }catch (ExceptionTienda e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "products/{idProduct}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long idProducto){
        try{
            productServices.deleteProduct(idProducto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (ExceptionTienda e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
