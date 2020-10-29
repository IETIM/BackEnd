package edu.eci.ieti.ProjectIeti.services.impl;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payment;
import edu.eci.ieti.ProjectIeti.exceptions.ShopException;
import edu.eci.ieti.ProjectIeti.config.PaypalConfig;
import edu.eci.ieti.ProjectIeti.model.Order;
import edu.eci.ieti.ProjectIeti.model.Shop;
import edu.eci.ieti.ProjectIeti.persistence.ShopRepository;
import edu.eci.ieti.ProjectIeti.services.PayServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class PayPalServicesImpl implements PayServices {

    APIContext apiContext;

    @Autowired
    private ShopRepository tiendaRepository;


    @Override
    public Payment createPayment(Order order,
                                 String cancelUrl,
                                 String successUrl) throws PayPalRESTException, ShopException {
        setPaymentConfiguration(order.getShop());
        Amount amount = new Amount();
        DecimalFormat df = new DecimalFormat("#.##");
        amount.setTotal(df.format(order.getTotal()));
        amount.setCurrency(order.getCurrency());

        Transaction transaction = new Transaction();
        transaction.setDescription(order.getDescription());
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(order.getMethod());

        Payment payment = new Payment();
        payment.setIntent(order.getIntent());
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    @Override
    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);
        PaymentExecution paymentExecute = new PaymentExecution();
        paymentExecute.setPayerId(payerId);
        return payment.execute(apiContext, paymentExecute);
    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void setPaymentConfiguration(String shop) throws PayPalRESTException, ShopException {
        PaypalConfig pc = new PaypalConfig();
        Shop tr = tiendaRepository.findByName(shop).orElseThrow(() -> new ShopException(ShopException.SHOP_NOT_FOUND));
        pc.setClientId(tr.getApiClient());
        pc.setClientSecret(tr.getApiSecret());
        pc.setMode("sandbox");
        apiContext = pc.apiContext();
    }
}
