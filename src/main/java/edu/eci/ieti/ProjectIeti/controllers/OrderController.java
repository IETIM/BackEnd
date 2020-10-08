package edu.eci.ieti.ProjectIeti.controllers;

import edu.eci.ieti.ProjectIeti.Exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.model.Order;
import edu.eci.ieti.ProjectIeti.model.Shop;
import edu.eci.ieti.ProjectIeti.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(path="/{shopName}")
    public ResponseEntity<?> getOrdersByShop(@PathVariable String shopName){
        try{

            return new ResponseEntity<>(orderServices.getOrdersByShop(shopName), HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("SHOP DOES NOT EXISTS", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteOrderById(@PathVariable String id){
        try{
            orderServices.cancelOrder(id);
            return new ResponseEntity<>("Order Canceled! :D",HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("There was an error,Order Not Found :(",HttpStatus.NOT_FOUND);
    }
}

