package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.model.ERole;
import edu.eci.ieti.ProjectIeti.model.Role;
import edu.eci.ieti.ProjectIeti.model.User;
import edu.eci.ieti.ProjectIeti.persistence.RoleRepository;
import edu.eci.ieti.ProjectIeti.persistence.UserRepository;
import edu.eci.ieti.ProjectIeti.exceptions.UserException;
import edu.eci.ieti.ProjectIeti.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServicesImpl implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void addUser(User user) throws UserException {

        Optional<User> optionalUser = userRepository.getUserByEmail(user.getEmail());
        if (optionalUser.isPresent()) {
            throw new UserException(UserException.USER_REGISTERED);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            List<ERole> roles = new ArrayList<>();
            user.getAuthorities().forEach(e -> roles.add(e.getRole()));
            Role role = roleRepository.findByRole(roles.get(0));
            List rolesN = new ArrayList();
            rolesN.add(role);
            user.setAuthorities(rolesN);
            userRepository.save(user);
        }
    }

    @Override
    public User getUserByEmail(String email) throws UserException {

        return userRepository.getUserByEmail(email).orElseThrow(() -> new UserException(UserException.USER_NOT_FOUND));
    }

    @Override
    public void update(String email , User user) throws UserException {
        Optional<User> usuarioOpcional = userRepository.getUserByEmail(email);
        if(!usuarioOpcional.isPresent()){
            throw new UserException(UserException.USER_NOT_FOUND);
        }
        else {
            User usuarioActual=usuarioOpcional.get();
            if(user.getName()!=null && !user.getName().equals("")){
                System.out.println("hola");
                usuarioActual.setName(user.getName());
            }
            if(user.getEmail()!=null && !user.getEmail().equals("") ){
                usuarioActual.setEmail(user.getEmail());
            }
            if(user.getPassword()!=null && !user.getPassword().equals("")){
                usuarioActual.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            if(user.getAddress()!=null && !user.getAddress().equals("")){
                usuarioActual.setAddress(user.getAddress());
            }
            if(user.getCellphone()!=null && !user.getCellphone().equals("") ){
                usuarioActual.setCellphone(user.getCellphone());
            }
            userRepository.save(usuarioActual);
        }
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
