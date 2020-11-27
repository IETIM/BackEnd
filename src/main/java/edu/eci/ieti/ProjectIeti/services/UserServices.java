package edu.eci.ieti.ProjectIeti.services;

import edu.eci.ieti.ProjectIeti.exceptions.UserException;
import edu.eci.ieti.ProjectIeti.model.User;

import java.util.List;

public interface UserServices {

     void addUser(User user) throws UserException;

     User getUserByEmail(String email) throws UserException;

     void update (String email, User user)throws UserException;

     List<User> getAllUsers();






}
