package edu.eci.ieti.ProjectIeti.controllers;

import edu.eci.ieti.ProjectIeti.exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.exceptions.UserException;
import edu.eci.ieti.ProjectIeti.model.Storekeeper;
import edu.eci.ieti.ProjectIeti.services.StorekeeperServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/storekeeper")
public class StorekeeperController {
    @Autowired
    private StorekeeperServices storekeeperServices;

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody Storekeeper user){
        try{
            storekeeperServices.addStorekeeper(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }catch (UserException | ShopException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{userId}")
    public  ResponseEntity<?> updateUser(@PathVariable String userId,@RequestBody Storekeeper user){
        try{
            user.setId(userId);
            storekeeperServices.update(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (UserException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getStorekeeperByEmail(@PathVariable String email){
        try{
            return ResponseEntity.ok(storekeeperServices.getStorekeeperByEmail(email));
        }
        catch (UserException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
