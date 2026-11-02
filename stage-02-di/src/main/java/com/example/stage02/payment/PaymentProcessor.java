package com.example.stage02.payment;

/** A payment strategy that can identify itself and process a payment. */
public interface PaymentProcessor {

    /** A short label used by the demo output. */
    String name();

    /** Returns a message showing which strategy handled the payment. */
    String process();
}
