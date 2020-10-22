package edu.eci.ieti.ProjectIeti.services;

import edu.eci.ieti.ProjectIeti.exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.model.Order;

import java.util.List;

public interface OrderServices {
    public Order createOrder(Order o);

    public Order getOrder(String orderId) throws ShopException;

    public void payOrder(String orderId);

    public List<Order> getOrdersByShop(String shopName);

    public void cancelOrder(String orderId);


}
