package edu.eci.ieti.ProjectIeti.services;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionShop;
import edu.eci.ieti.ProjectIeti.model.Order;

public interface PayServices {
    Payment createPayment(Order order,
                          String cancelUrl,
                          String successUrl) throws PayPalRESTException, ExceptionShop;

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;

}
