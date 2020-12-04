package edu.eci.ieti.ProjectIeti.controllers;

import edu.eci.ieti.ProjectIeti.exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.model.Order;
import edu.eci.ieti.ProjectIeti.services.OrderServices;
import edu.eci.ieti.ProjectIeti.services.PayServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
@CrossOrigin("*")
@Controller
public class PaypalController {

    @Autowired
    PayServices service;
    @Autowired
    OrderServices orderServices;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";
    @Value("${application.frontend.url}")
    private String frontURL;

    @PostMapping("/pay/{orderId}")  
    public ResponseEntity<?> payment(@PathVariable String orderId) {
        try {
            Order order = orderServices.getOrder(orderId);
            Payment payment = service.createPayment(order, frontURL+CANCEL_URL,
                    frontURL+SUCCESS_URL);

            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return new ResponseEntity<>(link.getHref(), HttpStatus.OK);
                }
            }

        } catch (PayPalRESTException | ShopException e) {

            e.printStackTrace();
        }
        return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(value = SUCCESS_URL)
    public ResponseEntity<?> successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return new ResponseEntity<>("Success", HttpStatus.OK);
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return new ResponseEntity<>("Fail", HttpStatus.CONFLICT);
    }

}
