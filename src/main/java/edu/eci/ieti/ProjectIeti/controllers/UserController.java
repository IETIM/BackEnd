package edu.eci.ieti.ProjectIeti.controllers;

import edu.eci.ieti.ProjectIeti.model.User;
import edu.eci.ieti.ProjectIeti.services.ExceptionProject;
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

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody User user){
        try{
            userServices.addUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }catch (ExceptionProject e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
