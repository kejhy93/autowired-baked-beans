package com.example.stage02.payment;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Map;

@Configuration(proxyBeanMethods = false)
@Import({CardPaymentProcessor.class, BankTransferPaymentProcessor.class, VoucherPaymentProcessor.class})
public class PaymentConfig {

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
