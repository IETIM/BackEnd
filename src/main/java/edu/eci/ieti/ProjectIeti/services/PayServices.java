package edu.eci.ieti.ProjectIeti.services;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionTienda;

public interface PayServices {
    Payment createPayment(String shop,
                          Double total,
                          String currency,
                          String method,
                          String intent,
                          String description,
                          String cancelUrl,
                          String successUrl) throws PayPalRESTException, ExceptionTienda;

    Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;

}
