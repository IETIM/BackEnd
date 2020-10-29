package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.Exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.Exceptions.UserException;
import edu.eci.ieti.ProjectIeti.model.ERole;
import edu.eci.ieti.ProjectIeti.model.Role;
import edu.eci.ieti.ProjectIeti.model.Storekeeper;
import edu.eci.ieti.ProjectIeti.model.User;
import edu.eci.ieti.ProjectIeti.persistence.OrderRepository;
import edu.eci.ieti.ProjectIeti.persistence.RoleRepository;
import edu.eci.ieti.ProjectIeti.persistence.ShopRepository;
import edu.eci.ieti.ProjectIeti.persistence.StorekeeperRepository;
import edu.eci.ieti.ProjectIeti.services.ShopServices;
import edu.eci.ieti.ProjectIeti.services.StorekeeperServices;
import edu.eci.ieti.ProjectIeti.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StorekeeperServicesImpl implements StorekeeperServices {

    @Autowired
    private StorekeeperRepository storekeeperRepository;

    @Autowired
    private ShopServices shopServices;

    @Autowired
    private UserServices userServices;

    @Autowired
    private RoleRepository roleRepository;



    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void addStorekeeper(Storekeeper user) throws UserException, ShopException {
        Optional<Storekeeper> findStorekeeper = storekeeperRepository.getStorekeeperByEmail(user.getEmail());
        if (findStorekeeper.isPresent()) {
            throw new UserException(UserException.USER_REGISTERED);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            shopServices.addShop(user.getShop());
            User newUser = new User(user.getEmail(),user.getName(),user.getPassword(),null,user.getCellphone());
            List<Role> roles = new ArrayList();
            Role rol = roleRepository.findByRole(ERole.ROLE_TENDERO);
            roles.add(rol);
            newUser.setAuthorities(roles);
            userServices.addUser(newUser);
            storekeeperRepository.save(user);
        }
    }

    @Override
    public Storekeeper getStorekeeperByEmail(String email) throws UserException {
        return storekeeperRepository.getStorekeeperByEmail(email).orElseThrow(() -> new UserException(UserException.USER_NOT_FOUND));
    }

    @Override
    public void update(Storekeeper user) throws UserException {
        Optional<Storekeeper> optionalStorekeeper = storekeeperRepository.getStorekeeperByEmail(user.getEmail());
        Storekeeper actualStoreK = optionalStorekeeper.orElseThrow(() -> new UserException(UserException.USER_NOT_FOUND));
        if(user.getEmail()!=null){
            actualStoreK.setEmail(user.getEmail());
        }
        if(user.getName()!=null){
            actualStoreK.setName(user.getName());
        }
        if( user.getCellphone()!=null){
            actualStoreK.setCellphone(user.getCellphone());
        }
        if(user.getPassword()!=null){
            actualStoreK.setPassword(user.getPassword());
        }
        storekeeperRepository.save(actualStoreK);
    }
}

