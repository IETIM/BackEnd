package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.model.Order;
import edu.eci.ieti.ProjectIeti.model.Product;
import edu.eci.ieti.ProjectIeti.model.Purchase;
import edu.eci.ieti.ProjectIeti.persistence.OrderRepository;
import edu.eci.ieti.ProjectIeti.persistence.ProductRepository;
import edu.eci.ieti.ProjectIeti.persistence.PurchaseRepository;
import edu.eci.ieti.ProjectIeti.persistence.ShopRepository;
import edu.eci.ieti.ProjectIeti.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServicesImpl implements OrderServices {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    PurchaseRepository purchaseRepository;
    @Autowired
    ShopRepository shopRepository;


    @Override
    public Order createOrder(Order o) {
        ArrayList<Purchase> purchases = o.getPurchases();
        double total= calculatePrice(purchases);
        o.setTotal(total);
        o.setIntent("sale");
        o.setState("new");
        purchaseRepository.saveAll(purchases);
        return orderRepository.save(o);
    }

    @Override
    public Order getOrder(String orderId) throws ShopException {
        Optional<Order> o =  orderRepository.findById(orderId);
        Order ord = o.orElseThrow(() -> new ShopException(ShopException.ORDER_NOT_FOUND));
        return ord;
    }

    @Override
    public void payOrder(String orderId) {
    }

    @Override
    public List<Order> getOrdersByShop(String shopName) {
        return orderRepository.findAllByShop(shopName);
    }

    @Override
    public void cancelOrder(String orderId) {
        orderRepository.deleteById(orderId);
    }


    private double calculatePrice(ArrayList<Purchase> products) {
        double total = 0;
        for (Purchase p: products){
            Product pOriginal = productRepository.findById(p.getProductId()).get();
            total+= p.getQuantity() * pOriginal.getPrice();
        }
        return total;
    }
}
