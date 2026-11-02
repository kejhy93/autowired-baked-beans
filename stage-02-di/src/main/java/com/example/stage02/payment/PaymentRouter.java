package com.example.stage02.payment;

import java.util.List;
import java.util.Map;

/**
 * Holds the payment choices injected by Spring, including one default, qualified choices, and all candidates.
 * The collections are copied so callers cannot change the router's view after construction.
 */
public class PaymentRouter {

    private final PaymentProcessor defaultProcessor;
    private final PaymentProcessor bankTransferProcessor;
    private final PaymentProcessor bankRailProcessor;
    private final List<PaymentProcessor> allProcessors;
    private final Map<String, PaymentProcessor> processorsByBeanName;

    /**
     * Captures single-bean and multi-bean injection in one object for the learning example.
     */
    public PaymentRouter(PaymentProcessor defaultProcessor,
                         PaymentProcessor bankTransferProcessor,
                         PaymentProcessor bankRailProcessor,
                         List<PaymentProcessor> allProcessors,
                         Map<String, PaymentProcessor> processorsByBeanName) {
        this.defaultProcessor = defaultProcessor;
        this.bankTransferProcessor = bankTransferProcessor;
        this.bankRailProcessor = bankRailProcessor;
        this.allProcessors = List.copyOf(allProcessors);
        this.processorsByBeanName = Map.copyOf(processorsByBeanName);
    }

    public PaymentProcessor defaultProcessor() {
        return defaultProcessor;
    }

    public PaymentProcessor bankTransferProcessor() {
        return bankTransferProcessor;
    }

    public PaymentProcessor bankRailProcessor() {
        return bankRailProcessor;
    }

    public List<PaymentProcessor> allProcessors() {
        return allProcessors;
    }

    public Map<String, PaymentProcessor> processorsByBeanName() {
        return processorsByBeanName;
    }

    /** Selects a strategy by the corresponding Spring bean name. */
    public String route(String processorBeanName) {
        return processorsByBeanName.get(processorBeanName).process();
    }
}
