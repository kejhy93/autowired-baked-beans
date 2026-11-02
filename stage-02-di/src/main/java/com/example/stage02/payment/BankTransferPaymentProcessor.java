package com.example.stage02.payment;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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
