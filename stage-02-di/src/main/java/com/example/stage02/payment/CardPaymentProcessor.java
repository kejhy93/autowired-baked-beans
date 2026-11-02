package com.example.stage02.payment;

import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** The primary card strategy, also used to demonstrate custom qualifier matching and collection order. */
@Component("cardPaymentProcessor")
@Primary
@Order(1)
@PaymentRail("card")
public class CardPaymentProcessor implements PaymentProcessor {

    @Override
    public String name() {
        return "card";
    }

    @Override
    public String process() {
        return "paid by card";
    }
}
