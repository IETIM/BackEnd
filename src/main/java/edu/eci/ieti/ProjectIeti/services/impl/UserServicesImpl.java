package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.model.User;
import edu.eci.ieti.ProjectIeti.persistence.UserRepository;
import edu.eci.ieti.ProjectIeti.services.ExceptionProject;
import edu.eci.ieti.ProjectIeti.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(User user) throws ExceptionProject {

        Optional<User> optionalUser = userRepository.getUserByCorreo(user.getCorreo());
        if (optionalUser.isPresent()) {
            throw new ExceptionProject(ExceptionProject.USER_REGISTERED);
        } else {
            userRepository.save(user);
        }

    }

    @Override
    public User getUserByCorreo(String correo) {
        return userRepository.getUserByCorreo(correo).get();
    }

    @Override
    public void update(User user) {

    }
}
