package edu.eci.ieti.ProjectIeti.controllers;


import edu.eci.ieti.ProjectIeti.Exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderServices orderServices;

    @GetMapping(path = "/{shop}/orders")
    public ResponseEntity<?> getOrdersByShop(@PathVariable String shop){
        try {
            return new ResponseEntity<>(orderServices.getOrdersByShop(shop), HttpStatus.ACCEPTED);
        } catch (ShopException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
