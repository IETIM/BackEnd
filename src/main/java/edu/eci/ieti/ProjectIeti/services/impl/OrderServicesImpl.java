package edu.eci.ieti.ProjectIeti.services.impl;

import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionShop;
import edu.eci.ieti.ProjectIeti.model.Order;
import edu.eci.ieti.ProjectIeti.persistence.OrderRepository;
import edu.eci.ieti.ProjectIeti.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderServicesImpl implements OrderServices {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order createOrder(Order o) {
        //Revisar
        return orderRepository.insert(o);
    }

    @Override
    public Order getOrder(String orderId) throws ExceptionShop {
        Optional<Order> o =  orderRepository.findById(orderId);
        Order ord = o.orElseThrow(() -> new ExceptionShop(ExceptionShop.ORDER_NOT_FOUND));
        return ord;
    }

    @Override
    public void payOrder(String orderId) {

    }
}
