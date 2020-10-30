package edu.eci.ieti.ProjectIeti.services;

import edu.eci.ieti.ProjectIeti.exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.model.Shop;

import java.util.List;

public interface ShopServices {

    List<Shop> getShops();

    List<Shop> getShopsByType(String type);

    Shop getShopsById(String id) throws ShopException;

    Shop addShop(Shop shop) throws ShopException;

}

