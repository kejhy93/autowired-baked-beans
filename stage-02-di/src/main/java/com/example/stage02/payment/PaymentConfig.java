package com.example.stage02.payment;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Map;

/**
 * Registers the three payment candidates and builds a router using several injection forms.
 */
@Configuration(proxyBeanMethods = false)
@Import({CardPaymentProcessor.class, BankTransferPaymentProcessor.class, VoucherPaymentProcessor.class})
public class PaymentConfig {

    /**
     * Demonstrates primary selection, two qualifier forms, ordered list injection, and a bean-name map.
     *
     * @param defaultProcessor unqualified dependency resolved to the {@code @Primary} card processor
     * @param bankTransferProcessor bean selected by its qualifier value
     * @param bankRailProcessor bean selected by the custom {@link PaymentRail} qualifier
     * @param allProcessors every matching processor, ordered by {@code @Order}
     * @param processorsByBeanName every matching processor keyed by its bean name
     */
    @Bean
    public PaymentRouter paymentRouter(PaymentProcessor defaultProcessor,
                                       @Qualifier("bankTransferPaymentProcessor") PaymentProcessor bankTransferProcessor,
                                       @PaymentRail("bank") PaymentProcessor bankRailProcessor,
                                       List<PaymentProcessor> allProcessors,
                                       Map<String, PaymentProcessor> processorsByBeanName) {
        return new PaymentRouter(defaultProcessor, bankTransferProcessor, bankRailProcessor,
                allProcessors, processorsByBeanName);
    }
}
