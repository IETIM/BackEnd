package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.exceptions.ShopException;
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
    public List<Product> getProducts(String idTienda) throws ShopException {
        Optional<Shop> findShop =shopRepository.findById(idTienda);
        Shop shop =findShop.orElseThrow(() -> new ShopException(ShopException.SHOP_NOT_FOUND));
        return shop.getProducts();
    }

    @Override
    public Product addProduct(Product product, String idTienda) throws ShopException {
        Optional<Shop> findShop = shopRepository.findById(idTienda);
        Shop shop =findShop.orElseThrow(() -> new ShopException(ShopException.SHOP_NOT_FOUND));
        productRepository.save(product);
        shop.getProducts().add(product);
        shopRepository.save(shop);
        return product;
    }

    @Override
    public Product updateProduct(Product product, String idProduct) throws ShopException {
        Optional<Product> optionalProduct= productRepository.findById(idProduct);
        Product actualProduct = optionalProduct.orElseThrow(() -> new ShopException(ShopException.PRODUCT_NOT_FOUND));
        if(product.getImage()!=null && !product.getImage().equals("")){
            actualProduct.setImage(product.getImage());
        }
        if(product.getDescription()!=null && !product.getCategory().equals("")){
            actualProduct.setDescription(product.getDescription());
        }
        if(product.getName()!=null && !product.getName().equals("")){
            actualProduct.setName(product.getName());
        }
        if(product.getPrice()!=null ){
            actualProduct.setPrice(product.getPrice());
        }
        if(product.getStocks()!=null){
            actualProduct.setStocks(product.getStocks());
        }
        if(product.getCategory()!=null && !product.getCategory().equals("")){
            actualProduct.setCategory(product.getCategory());
        }
        productRepository.save(actualProduct);
        return actualProduct;
    }

    @Override
    public void deleteProduct(String idProduct) throws ShopException {
        productRepository.deleteById(idProduct);
    }

    @Override
    public Product getProductById(String id) {
        return productRepository.findById(id).get();
    }
}
