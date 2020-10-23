package edu.eci.ieti.ProjectIeti.services;

import edu.eci.ieti.ProjectIeti.Exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.model.Order;

import java.util.List;
import java.util.Optional;

public interface OrderServices {
    public Order createOrder(Order o);

    public Order getOrder(String orderId) throws ShopException;

    public void payOrder(String orderId);

    public List<Order> getOrdersByShop(String shopName);

    public List<Order> getOrdersByUser(String user);

    public void cancelOrder(String orderId);


}
