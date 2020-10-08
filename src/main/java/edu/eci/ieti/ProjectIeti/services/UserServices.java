package edu.eci.ieti.ProjectIeti.services;

import edu.eci.ieti.ProjectIeti.Exceptions.UserException;
import edu.eci.ieti.ProjectIeti.model.Role;
import edu.eci.ieti.ProjectIeti.model.User;

import java.util.List;

public interface UserServices {

     void addUser(User user) throws UserException;

     User getUserByEmail(String email) throws UserException;

     void update (User user)throws UserException;

     Role getRole (String userId) throws UserException;

     List<User> getAllUsers();






}
