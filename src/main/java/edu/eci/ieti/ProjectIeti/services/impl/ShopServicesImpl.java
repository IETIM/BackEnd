package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.Exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.model.Product;
import edu.eci.ieti.ProjectIeti.model.Shop;
import edu.eci.ieti.ProjectIeti.persistence.ProductRepository;
import edu.eci.ieti.ProjectIeti.persistence.ShopRepository;
import edu.eci.ieti.ProjectIeti.services.ProductServices;
import edu.eci.ieti.ProjectIeti.services.ShopServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Shop getShopsByName(String name) throws ShopException {
        return shopRepository.findByName(name).orElseThrow(() -> new ShopException(ShopException.SHOP_NOT_FOUND));
    }

    @Override
    public void addShop(Shop shop) throws ShopException {

        if(shopRepository.findByName(shop.getName()).isPresent()){
            throw new ShopException(ShopException.SHOP_REGISTERED);
        }
        else {
            for (Product product : shop.getProducts()){
                productRepository.save(product);
            }
            shopRepository.save(shop);
        }
    }
}
