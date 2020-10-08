package edu.eci.ieti.ProjectIeti.services;

import edu.eci.ieti.ProjectIeti.Exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.Exceptions.UserException;
import edu.eci.ieti.ProjectIeti.model.Storekeeper;

public interface StorekeeperServices {

    void addStorekeeper(Storekeeper user) throws UserException, ShopException;

    Storekeeper getStorekeeperByEmail(String email) throws UserException;

    void update (Storekeeper user) throws UserException;

}
