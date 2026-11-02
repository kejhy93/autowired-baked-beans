package com.example.stage02.injection;

import com.example.stage02.payment.PaymentProcessor;

public class ConstructorInjectedCheckout {

    private final PaymentProcessor processor;

    public ConstructorInjectedCheckout(PaymentProcessor processor) {
        this.processor = processor;
    }

    public String checkout() {
        return processor.process();
    }
}
