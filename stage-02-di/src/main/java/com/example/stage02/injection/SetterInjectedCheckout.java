package com.example.stage02.injection;

import com.example.stage02.payment.PaymentProcessor;

public class SetterInjectedCheckout {

    private PaymentProcessor processor;

    public void setProcessor(PaymentProcessor processor) {
        this.processor = processor;
    }

    public String checkout() {
        if (processor == null) {
            return "no payment processor configured";
        }
        return processor.process();
    }
}
