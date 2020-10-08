package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.model.Role;
import edu.eci.ieti.ProjectIeti.model.User;
import edu.eci.ieti.ProjectIeti.persistence.UserRepository;
import edu.eci.ieti.ProjectIeti.Exceptions.UserException;
import edu.eci.ieti.ProjectIeti.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void addUser(User user) throws UserException {

        Optional<User> optionalUser = userRepository.getUserByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new UserException(UserException.USER_REGISTERED);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    @Override
    public User getUserByEmail(String email) throws UserException {

        return userRepository.getUserByEmail(email).orElseThrow(() -> new UserException(UserException.USER_NOT_FOUND));
    }

    @Override
    public void update(User user) throws UserException {
        Optional<User> usuarioOpcional=userRepository.findById(user.getId());
        if(!usuarioOpcional.isPresent()){
            throw new UserException(UserException.USER_NOT_FOUND);
        }
        else {
            User usuarioActual=usuarioOpcional.get();
            if(user.getName()!=null){
                usuarioActual.setName(user.getName());
            }
            if(user.getEmail()!=null){
                usuarioActual.setEmail(user.getEmail());
            }
            if(user.getPassword()!=null){
                usuarioActual.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userRepository.save(usuarioActual);
        }
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
