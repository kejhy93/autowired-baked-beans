package com.example.stage02.payment;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** A regular candidate selected by either its bean-name qualifier or the {@code bank} rail qualifier. */
@Component("bankTransferPaymentProcessor")
@Order(2)
@PaymentRail("bank")
public class BankTransferPaymentProcessor implements PaymentProcessor {

    @Override
    public String name() {
        return "bank transfer";
    }

    @Override
    public String process() {
        return "paid by bank transfer";
    }
}
