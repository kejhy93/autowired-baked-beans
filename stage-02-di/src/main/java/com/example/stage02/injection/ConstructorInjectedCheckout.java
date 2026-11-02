package com.example.stage02.injection;

import com.example.stage02.payment.PaymentProcessor;

/** Requires its processor at construction time, so it can never be created in an incomplete state. */
public class ConstructorInjectedCheckout {

    private final PaymentProcessor processor;

    public ConstructorInjectedCheckout(PaymentProcessor processor) {
        this.processor = processor;
    }

    public String checkout() {
        return processor.process();
    }
}
