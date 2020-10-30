package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.model.Shop;
import edu.eci.ieti.ProjectIeti.persistence.ProductRepository;
import edu.eci.ieti.ProjectIeti.persistence.ShopRepository;
import edu.eci.ieti.ProjectIeti.services.ShopServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
@CrossOrigin("*")
@Service
public class ShopServicesImpl implements ShopServices {

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Shop> getShops() {
        return shopRepository.findAll();
    }

    @Override
    public List<Shop> getShopsByType(String type) {
        return shopRepository.findAllByType(type);
    }

    @Override
    public Shop getShopsById(String id) throws ShopException {
        return shopRepository.findById(id).orElseThrow(() -> new ShopException(ShopException.SHOP_NOT_FOUND));
    }

    @Override
    public Shop addShop(Shop shop) throws ShopException {

        if(shopRepository.findByName(shop.getName()).isPresent()){
            throw new ShopException(ShopException.SHOP_REGISTERED);
        }
        else {
            productRepository.saveAll(shop.getProducts());
            shopRepository.save(shop);
        }
        return shop;
    }
}
