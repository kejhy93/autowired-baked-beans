package com.example.stage02.payment;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Map;

@Configuration(proxyBeanMethods = false)
@Import(VoucherPaymentProcessor.class)
public class FallbackOnlyPaymentConfig {

    @Bean
    public PaymentRouter paymentRouter(PaymentProcessor defaultProcessor,
                                       List<PaymentProcessor> allProcessors,
                                       Map<String, PaymentProcessor> processorsByBeanName) {
        return new PaymentRouter(defaultProcessor, defaultProcessor, defaultProcessor,
                allProcessors, processorsByBeanName);
    }
}
