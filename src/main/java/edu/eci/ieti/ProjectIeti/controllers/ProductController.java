package edu.eci.ieti.ProjectIeti.controllers;

import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionShop;
import edu.eci.ieti.ProjectIeti.model.Product;
import edu.eci.ieti.ProjectIeti.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/shops")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @GetMapping(path = "/{idShop}/products")
    public ResponseEntity<?> getProductsByShop(@PathVariable String idShop){
        try {
            return ResponseEntity.ok(productServices.getProducts(idShop));
        } catch (ExceptionShop e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/{idShop}/products")
    public ResponseEntity<?> addProduct(@RequestBody Product product, @PathVariable String idShop){
        try {
            productServices.addProduct(product,idShop);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (ExceptionShop e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(value = "/products/{idProduct}")
    public ResponseEntity<?> updateProduct(@RequestBody Product product, @PathVariable String idProduct){
        try{
            productServices.updateProduct(product,idProduct);
            return new ResponseEntity<>(HttpStatus.OK);


        }catch (ExceptionShop e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "products/{idProduct}")
    public ResponseEntity<?> deleteProduct(@PathVariable String idProduct){
        try{
            productServices.deleteProduct(idProduct);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (ExceptionShop e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
