package edu.eci.ieti.ProjectIeti.controllers;

import edu.eci.ieti.ProjectIeti.exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.model.Product;
import edu.eci.ieti.ProjectIeti.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*")
@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductServices productServices;


    @GetMapping(path = "/{idShop}")
    public ResponseEntity<?> getProductsByShop(@PathVariable String idShop){
        try {
            return ResponseEntity.ok(productServices.getProducts(idShop));
        } catch (ShopException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/{idShop}")
    public ResponseEntity<?> addProduct(@RequestBody Product product, @PathVariable String idShop){
        try {
            Product addedProduct= productServices.addProduct(product,idShop);
            return new ResponseEntity<>(addedProduct ,HttpStatus.CREATED);
        }catch (ShopException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping(value = "/{idProduct}")
    public ResponseEntity<?> updateProduct(@RequestBody Product product, @PathVariable String idProduct){
        try{
            Product updatedProduct= productServices.updateProduct(product,idProduct);
            return new ResponseEntity<>(updatedProduct,HttpStatus.OK);


        }catch (ShopException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{idProduct}")
    public ResponseEntity<?> deleteProduct(@PathVariable String idProduct){
        try{
            productServices.deleteProduct(idProduct);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (ShopException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
