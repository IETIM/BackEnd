package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.model.User;
import edu.eci.ieti.ProjectIeti.persistence.UserRepository;
import edu.eci.ieti.ProjectIeti.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(User user) {

    }

    @Override
    public User getUserByCorreo(String correo) {
        return userRepository.getUserByCorreo(correo);
    }

    @Override
    public void update(User user) {

    }
}
