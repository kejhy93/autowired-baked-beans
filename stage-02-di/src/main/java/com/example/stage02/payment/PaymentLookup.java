package com.example.stage02.payment;

import org.springframework.beans.factory.ObjectProvider;

import java.util.stream.Stream;

/**
 * Shows that an {@link ObjectProvider} can be injected now and queried only when a caller needs a processor.
 */
public class PaymentLookup {

    private final ObjectProvider<PaymentProcessor> processors;

    public PaymentLookup(ObjectProvider<PaymentProcessor> processors) {
        this.processors = processors;
    }

    /** Returns the selected processor, or {@code null} when no candidate is available. */
    public PaymentProcessor availableProcessor() {
        return processors.getIfAvailable();
    }

    /** Returns a unique processor, or {@code null} when there is no unique candidate. */
    public PaymentProcessor uniqueProcessor() {
        return processors.getIfUnique();
    }

    /** Streams all candidates without collapsing them to the primary bean. */
    public Stream<PaymentProcessor> processors() {
        return processors.stream();
    }
}
