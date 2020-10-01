package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.Exceptions.UserException;
import edu.eci.ieti.ProjectIeti.model.Storekeeper;
import edu.eci.ieti.ProjectIeti.persistence.OrderRepository;
import edu.eci.ieti.ProjectIeti.persistence.StorekeeperRepository;
import edu.eci.ieti.ProjectIeti.services.StorekeeperServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StorekeeperServicesImpl implements StorekeeperServices {

    @Autowired
    private StorekeeperRepository storekeeperRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void addStorekeeper(Storekeeper user) throws UserException {
        Optional<Storekeeper> findStorekeeper =storekeeperRepository.findById(user.getEmail());
        Storekeeper storekeeper = findStorekeeper.orElseThrow(() -> new UserException(UserException.USER_REGISTERED));
        storekeeperRepository.save(storekeeper);
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
        if( user.getCellphone() != 0 ){
            actualStoreK.setCellphone(user.getCellphone());
        }
        if(user.getPassword()!=null){
            actualStoreK.setPassword(user.getPassword());
        }
        storekeeperRepository.save(actualStoreK);

    }


}
