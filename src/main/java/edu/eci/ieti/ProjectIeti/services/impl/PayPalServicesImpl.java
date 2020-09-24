package edu.eci.ieti.ProjectIeti.services.impl;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payment;
import edu.eci.ieti.ProjectIeti.Exceptions.ExceptionTienda;
import edu.eci.ieti.ProjectIeti.config.PaypalConfig;
import edu.eci.ieti.ProjectIeti.model.Tienda;
import edu.eci.ieti.ProjectIeti.persistence.TiendaRepository;
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
import java.util.Optional;

@Service
public class PayPalServicesImpl implements PayServices {

    APIContext apiContext;

    @Autowired
    private TiendaRepository tiendaRepository;


    @Override
    public Payment createPayment(String shop,
                                 Double total,
                                 String currency,
                                 String method,
                                 String intent,
                                 String description,
                                 String cancelUrl,
                                 String successUrl) throws PayPalRESTException, ExceptionTienda {
        setPaymentConfiguration(shop);
        Amount amount = new Amount();
        DecimalFormat df = new DecimalFormat("#.##");
        amount.setTotal(df.format(total));
        amount.setCurrency(currency);

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method.toString());

        Payment payment = new Payment();
        payment.setIntent(intent.toString());
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

    private void setPaymentConfiguration(String shop) throws PayPalRESTException, ExceptionTienda {
        PaypalConfig pc = new PaypalConfig();
        Tienda tr = tiendaRepository.findByNombre(shop).orElseThrow(() -> new ExceptionTienda(ExceptionTienda.TIENDA_NO_ENCONTRADA));
        String c="AQHEkaz_E0vSsZxJ6hF8pHnr8G1TZvKEKeT-G4r218xQJk0ckMCpz93ZJQXaIfPqcx5yatSBDsoIZiXc";
        String s = "EFdNXWp0xMkxRR65fEoMJif2fNVjv7pQieNVt3JxoQtn9LlGYnC_922IQ33AhLW5nKesELDEt4JS_VAq";
        pc.setClientId(tr.getApiClient());
        pc.setClientSecret(tr.getApiSecret());
        pc.setMode("sandbox");
        apiContext = pc.apiContext();
    }
}
