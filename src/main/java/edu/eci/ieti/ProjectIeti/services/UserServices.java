package edu.eci.ieti.ProjectIeti.services;

import edu.eci.ieti.ProjectIeti.model.User;

public interface UserServices {

     void addUser(User user);

     User getUserByCorreo(String correo);

     void update (User user);
}
