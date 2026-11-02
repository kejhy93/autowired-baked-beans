package com.example.stage02.injection;

import com.example.stage02.payment.PaymentProcessor;

/** Uses a setter to model a dependency that may be absent or reconfigured after construction. */
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
