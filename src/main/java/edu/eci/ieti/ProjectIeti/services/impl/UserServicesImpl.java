package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.model.User;
import edu.eci.ieti.ProjectIeti.persistence.UserRepository;
import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionProject;
import edu.eci.ieti.ProjectIeti.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void addUser(User user) throws ExceptionProject {

        Optional<User> optionalUser = userRepository.getUserByCorreo(user.getCorreo());
        if (optionalUser.isPresent()) {
            throw new ExceptionProject(ExceptionProject.USER_REGISTERED);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }

    }

    @Override
    public User getUserByCorreo(String correo) {
        return userRepository.getUserByCorreo(correo).get();
    }

    @Override
    public void update(User user) throws ExceptionProject {
        Optional<User> usuarioOpcional=userRepository.getUserById(user.getId());
        if(!usuarioOpcional.isPresent()){
            throw new ExceptionProject(ExceptionProject.USER_NOT_FOUND);
        }
        else {
            User usuarioActual=usuarioOpcional.get();
            usuarioActual.setName(user.getName());
            usuarioActual.setCorreo(user.getCorreo());
            usuarioActual.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(usuarioActual);
        }
    }
}
