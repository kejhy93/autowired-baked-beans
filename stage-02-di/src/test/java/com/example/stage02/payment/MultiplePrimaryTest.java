package com.example.stage02.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Confirms that two primary candidates produce an ambiguity instead of being treated as ranked priorities. */
class MultiplePrimaryTest {

    @Test
    void twoPrimaryCandidatesAreStillAmbiguous() {
        var context = new AnnotationConfigApplicationContext();
        context.register(TwoPrimaryConfig.class);

        try (context) {
            BeanCreationException exception = assertThrows(BeanCreationException.class, context::refresh);
            NoUniqueBeanDefinitionException cause = assertInstanceOf(
                    NoUniqueBeanDefinitionException.class, exception.getCause());
            assertTrue(cause.getMessage().contains("cardProcessor"));
            assertTrue(cause.getMessage().contains("bankTransferPaymentProcessor"));
        }
    }

    @Configuration(proxyBeanMethods = false)
    static class TwoPrimaryConfig {

        @Bean
        @Primary
        PaymentProcessor cardProcessor() {
            return new CardPaymentProcessor();
        }

        @Bean
        @Primary
        PaymentProcessor bankTransferPaymentProcessor() {
            return new BankTransferPaymentProcessor();
        }

        @Bean
        String selectedProcessor(PaymentProcessor processor) {
            return processor.name();
        }
    }
}
