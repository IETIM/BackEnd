package edu.eci.ieti.ProjectIeti.controllers;

import edu.eci.ieti.ProjectIeti.Exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.model.Order;
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

    @PostMapping(value = "/new")
    public ResponseEntity<?> newOrder(@RequestBody Order order){
        return new ResponseEntity<>(orderServices.createOrder(order),HttpStatus.CREATED);
    }
}

