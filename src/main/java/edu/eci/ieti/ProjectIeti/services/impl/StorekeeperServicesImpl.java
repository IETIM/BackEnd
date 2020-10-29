package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.exceptions.UserException;
import edu.eci.ieti.ProjectIeti.model.ERole;
import edu.eci.ieti.ProjectIeti.model.Role;
import edu.eci.ieti.ProjectIeti.model.Storekeeper;
import edu.eci.ieti.ProjectIeti.model.User;
import edu.eci.ieti.ProjectIeti.persistence.RoleRepository;
import edu.eci.ieti.ProjectIeti.persistence.StorekeeperRepository;
import edu.eci.ieti.ProjectIeti.services.ShopServices;
import edu.eci.ieti.ProjectIeti.services.StorekeeperServices;
import edu.eci.ieti.ProjectIeti.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
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
            shopServices.addShop(user.getShop());
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
        storekeeperRepository.save(actualStoreK);
    }
}

