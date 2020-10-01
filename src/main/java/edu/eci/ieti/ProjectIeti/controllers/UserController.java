package edu.eci.ieti.ProjectIeti.controllers;

import edu.eci.ieti.ProjectIeti.model.User;
import edu.eci.ieti.ProjectIeti.Exceptions.UserException;
import edu.eci.ieti.ProjectIeti.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/users/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email){
        try {
            return new ResponseEntity<>(userServices.getUserByEmail(email),HttpStatus.OK);
        } catch (UserException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody User user){
        try{
            userServices.addUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }catch (UserException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/users/{userId}")
    public  ResponseEntity<?> updateUser(@PathVariable String userId,@RequestBody User user){
        try{
            user.setId(userId);
            userServices.update(user);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (UserException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }

    }

}
