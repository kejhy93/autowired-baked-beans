package com.example.stage02.payment;

import org.springframework.context.annotation.Fallback;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/** A last-resort candidate: Spring chooses it only when no regular processor is available. */
@Component("voucherPaymentProcessor")
@Fallback
@Order(3)
public class VoucherPaymentProcessor implements PaymentProcessor {

    @Override
    public String name() {
        return "voucher";
    }

    @Override
    public String process() {
        return "paid by voucher";
    }
}
