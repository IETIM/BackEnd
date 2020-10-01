package edu.eci.ieti.ProjectIeti.services;

import edu.eci.ieti.ProjectIeti.Exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.model.Order;
import edu.eci.ieti.ProjectIeti.model.Shop;

import java.util.List;

public interface OrderServices {


    List<Order> getOrdersByShop(String shop) throws ShopException;

}
