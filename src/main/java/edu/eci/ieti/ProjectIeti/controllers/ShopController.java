package edu.eci.ieti.ProjectIeti.controllers;


import edu.eci.ieti.ProjectIeti.exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.model.Shop;
import edu.eci.ieti.ProjectIeti.services.ShopServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins="*")
@RequestMapping(value = "/shops")
public class ShopController {
    @Autowired
    private ShopServices shopServices;

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getShopByName(@PathVariable String id){
        try {
            return new ResponseEntity<>(shopServices.getShopsById(id), HttpStatus.OK);
        } catch (ShopException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping()
    public ResponseEntity<?> getShops(@RequestParam(name = "type") Optional<String> type){
        List<Shop>shops;
        if(type.isPresent()){
            shops=shopServices.getShopsByType(type.get());
        }
        else {
            shops=shopServices.getShops();
        }
        return new ResponseEntity<>(shops, HttpStatus.ACCEPTED);
    }
    @PostMapping()
    public ResponseEntity<?> addShop(@RequestBody Shop shop){
        try {
            Shop s = shopServices.addShop(shop);
            return new ResponseEntity<>(s,HttpStatus.CREATED);
        } catch (ShopException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }
    }
}
