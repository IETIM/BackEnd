package edu.eci.ieti.ProjectIeti.controllers;

import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionShop;
import edu.eci.ieti.ProjectIeti.model.Order;
import edu.eci.ieti.ProjectIeti.services.OrderServices;
import edu.eci.ieti.ProjectIeti.services.PayServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@Controller
public class PaypalController {

    @Autowired
    PayServices service;
    @Autowired
    OrderServices orderServices;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

    @PostMapping("/pay")
    public String payment(String orderId) {
        try {
            Order order = orderServices.getOrder(orderId);
            // SUCCESS AND CANCEL URL = DOMAIN+SECCESS_URL / CANCEL_URL
            System.out.println("MODOFOKIU LA CURRENCY ------------>" + order.getCurrency());
            System.out.println("MODOFOKIU EL PRICE ------------>" + order.getPrice());
            Payment payment = service.createPayment(order, "https://www.stanford.edu/",
                    "https://www.w3schools.com/");

            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }

        } catch (PayPalRESTException | ExceptionShop e) {

            e.printStackTrace();
        }
        return "redirect:/coñopayment";
    }

    @GetMapping(value = CANCEL_URL)
    public String cancelPay() {
        return "cancel";
    }

    @GetMapping(value = SUCCESS_URL)
    public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            System.out.println(payment.toJSON());
            if (payment.getState().equals("approved")) {
                return "success";
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return "redirect:/coñosuccesspay";
    }

}
