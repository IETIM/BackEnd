package edu.eci.ieti.ProjectIeti.services;

import edu.eci.ieti.ProjectIeti.Exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.model.Shop;

import java.util.List;

public interface ShopServices {

    List<Shop> getShops();

    List<Shop> getShopsByType(String type);

    Shop getShopsByName(String name) throws ShopException;

    void addShop(Shop shop) throws ShopException;

}

