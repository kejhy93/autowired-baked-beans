package com.example.stage02.payment;

import org.springframework.beans.factory.ObjectProvider;

import java.util.stream.Stream;

public class PaymentLookup {

    private final ObjectProvider<PaymentProcessor> processors;

    public PaymentLookup(ObjectProvider<PaymentProcessor> processors) {
        this.processors = processors;
    }

    public PaymentProcessor availableProcessor() {
        return processors.getIfAvailable();
    }

    public PaymentProcessor uniqueProcessor() {
        return processors.getIfUnique();
    }

    public Stream<PaymentProcessor> processors() {
        return processors.stream();
    }
}
