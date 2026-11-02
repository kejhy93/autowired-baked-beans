package com.example.stage02.payment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration(proxyBeanMethods = false)
@Import({CardPaymentProcessor.class, BankTransferPaymentProcessor.class, VoucherPaymentProcessor.class})
public class PaymentConfig {

    @Bean
    public PaymentRouter paymentRouter(PaymentProcessor defaultProcessor,
                                       @Qualifier("bankTransferPaymentProcessor") PaymentProcessor bankTransferProcessor,
                                       @PaymentRail("bank") PaymentProcessor bankRailProcessor,
                                       java.util.List<PaymentProcessor> allProcessors,
                                       java.util.Map<String, PaymentProcessor> processorsByBeanName) {
        return new PaymentRouter(defaultProcessor, bankTransferProcessor, bankRailProcessor,
                allProcessors, processorsByBeanName);
    }

    @Configuration(proxyBeanMethods = false)
    @Import(VoucherPaymentProcessor.class)
    public static class FallbackOnly {

        @Bean
        public PaymentRouter paymentRouter(PaymentProcessor defaultProcessor,
                                           java.util.List<PaymentProcessor> allProcessors,
                                           java.util.Map<String, PaymentProcessor> processorsByBeanName) {
            return new PaymentRouter(defaultProcessor, defaultProcessor, defaultProcessor,
                    allProcessors, processorsByBeanName);
        }
    }
}
