package edu.eci.ieti.ProjectIeti.services;

import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionShop;
import edu.eci.ieti.ProjectIeti.model.Order;

import java.util.Optional;

public interface OrderServices {
    public Order createOrder(Order o);

    public Order getOrder(String orderId) throws ExceptionShop;

    public void payOrder(String orderId);


}
