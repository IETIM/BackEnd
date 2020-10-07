package edu.eci.ieti.ProjectIeti.services;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import edu.eci.ieti.ProjectIeti.Exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.model.Order;

public interface PayServices {
    Payment createPayment(Order order,
                          String cancelUrl,
                          String successUrl) throws PayPalRESTException, ShopException;

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;

}
