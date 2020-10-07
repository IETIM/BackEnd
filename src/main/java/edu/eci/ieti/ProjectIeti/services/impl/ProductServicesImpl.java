package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionShop;
import edu.eci.ieti.ProjectIeti.model.Product;
import edu.eci.ieti.ProjectIeti.model.Shop;
import edu.eci.ieti.ProjectIeti.persistence.ProductRepository;
import edu.eci.ieti.ProjectIeti.persistence.ShopRepository;
import edu.eci.ieti.ProjectIeti.services.ProductServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServicesImpl implements ProductServices {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShopRepository shopRepository;


    @Override
    public List<Product> getProducts(String idTienda) throws ExceptionShop {
        Optional<Shop> findShop =shopRepository.findById(idTienda);
        Shop shop =findShop.orElseThrow(() -> new ExceptionShop(ExceptionShop.SHOP_NOT_FOUND));
        return shop.getProducts();
    }

    @Override
    public void addProduct(Product product, String idTienda) throws ExceptionShop {
        Optional<Shop> findShop =shopRepository.findById(idTienda);
        Shop shop =findShop.orElseThrow(() -> new ExceptionShop(ExceptionShop.SHOP_NOT_FOUND));
        productRepository.save(product);
        shop.getProducts().add(product);
        shopRepository.save(shop);
    }

    @Override
    public void updateProduct(Product product, String idProduct) throws ExceptionShop {
        Optional<Product> optionalProduct= productRepository.findById(idProduct);
        Product actualProduct = optionalProduct.orElseThrow(() -> new ExceptionShop(ExceptionShop.PRODUCT_NOT_FOUND));
        if(product.getDescription()!=null){
            actualProduct.setDescription(product.getDescription());
        }
        if(product.getName()!=null){
            actualProduct.setName(product.getName());
        }
        if(product.getPrice()!=null){
            actualProduct.setPrice(product.getPrice());
        }
        if(product.getStocks()!=null){
            actualProduct.setStocks(product.getStocks());
        }
        productRepository.save(actualProduct);
    }

    @Override
    public void deleteProduct(String idProduct) throws ExceptionShop {
        productRepository.deleteById(idProduct);
    }
}
