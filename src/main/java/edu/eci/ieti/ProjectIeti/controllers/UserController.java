package edu.eci.ieti.ProjectIeti.controllers;

import edu.eci.ieti.ProjectIeti.model.User;
import edu.eci.ieti.ProjectIeti.Exceptions.UserException;
import edu.eci.ieti.ProjectIeti.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins="*")
@RestController
@RequestMapping(value = "/")
public class UserController {

    @Autowired
    private UserServices userServices;

    @PostMapping("/register")
    public ResponseEntity<?> addUser(@RequestBody User user, Authentication auth){
        try{
            userServices.addUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }catch (UserException e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/{userId}")
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

    @GetMapping(path = "/{token}")
    public ResponseEntity<?> getRole(@PathVariable Authentication token){
        return ResponseEntity.ok(token.getAuthorities());
    }
    @GetMapping(path="/username")
    public ResponseEntity<?> getUsername(Authentication auth){
        return new ResponseEntity<>(auth.getName(),HttpStatus.OK);
    }
}
