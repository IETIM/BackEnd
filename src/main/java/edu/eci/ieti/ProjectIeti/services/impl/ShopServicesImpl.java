package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionShop;
import edu.eci.ieti.ProjectIeti.model.Shop;
import edu.eci.ieti.ProjectIeti.persistence.ShopRepository;
import edu.eci.ieti.ProjectIeti.services.ShopServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopServicesImpl implements ShopServices {

    @Autowired
    private ShopRepository shopRepository;

    @Override
    public List<Shop> getShops() {
        return shopRepository.findAll();
    }

    @Override
    public List<Shop> getShopsByType(String type) {
        return shopRepository.findAllByType(type);
    }

    @Override
    public Shop getShopsByName(String name) throws ExceptionShop {
        return shopRepository.findByName(name).orElseThrow(() -> new ExceptionShop(ExceptionShop.SHOP_NOT_FOUND));
    }

    @Override
    public void addShop(Shop shop) throws ExceptionShop {
        if(shopRepository.findByName(shop.getName()).isPresent()){
            throw new ExceptionShop(ExceptionShop.SHOP_REGISTERED);
        }
        else {
            shopRepository.save(shop);
        }
    }


}
