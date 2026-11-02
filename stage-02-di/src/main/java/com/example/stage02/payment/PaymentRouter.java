package com.example.stage02.payment;

import java.util.List;
import java.util.Map;

public class PaymentRouter {

    private final PaymentProcessor defaultProcessor;
    private final PaymentProcessor bankTransferProcessor;
    private final PaymentProcessor bankRailProcessor;
    private final List<PaymentProcessor> allProcessors;
    private final Map<String, PaymentProcessor> processorsByBeanName;

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

    public String route(String processorBeanName) {
        return processorsByBeanName.get(processorBeanName).process();
    }
}
