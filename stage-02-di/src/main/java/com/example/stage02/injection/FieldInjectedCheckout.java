package com.example.stage02.injection;

import com.example.stage02.payment.PaymentProcessor;
import org.springframework.beans.factory.annotation.Autowired;

public class FieldInjectedCheckout {

    @Autowired
    PaymentProcessor processor;

    public String checkout() {
        return processor.process();
    }
}
