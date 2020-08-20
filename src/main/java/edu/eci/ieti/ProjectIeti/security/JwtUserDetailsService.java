package edu.eci.ieti.ProjectIeti.security;


import edu.eci.ieti.ProjectIeti.model.User;
import edu.eci.ieti.ProjectIeti.persistence.UserRepository;
import edu.eci.ieti.ProjectIeti.services.ExceptionProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.getUserByCorreo(s).get();
    }
}
