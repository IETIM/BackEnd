package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.Exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.model.Order;
import edu.eci.ieti.ProjectIeti.model.Product;
import edu.eci.ieti.ProjectIeti.model.Tuple;
import edu.eci.ieti.ProjectIeti.persistence.OrderRepository;
import edu.eci.ieti.ProjectIeti.persistence.ProductRepository;
import edu.eci.ieti.ProjectIeti.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class OrderServicesImpl implements OrderServices {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;

    @Override
    public Order createOrder(Order o) {
        double total= calculatePrice(o.getProducts());
        o.setTotal(total);

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

    private double calculatePrice(ArrayList<Tuple<String,Integer>> products) {
        double total = 0;
        for (Tuple<String,Integer> p: products){
            Product pOriginal = productRepository.findById(p.getElem1()).get();
            total+= p.getElem2() * pOriginal.getPrice();
        }
        return total;
    }
}
