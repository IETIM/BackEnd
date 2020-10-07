package edu.eci.ieti.ProjectIeti.services;

import edu.eci.ieti.ProjectIeti.Exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.model.Order;

import java.util.Optional;

public interface OrderServices {
    public Order createOrder(Order o);

    public Order getOrder(String orderId) throws ShopException;

    public void payOrder(String orderId);


}
