package com.example.stage02.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PaymentLookupTest {

    @Test
    void providerDefersLookupAndHandlesNoCandidateWithoutFailingStartup() {
        try (var context = new AnnotationConfigApplicationContext(LookupConfig.class)) {
            PaymentLookup lookup = context.getBean(PaymentLookup.class);

            assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(PaymentProcessor.class));
            assertNull(lookup.availableProcessor());
            assertNull(lookup.uniqueProcessor());
            assertEquals(0, lookup.processors().count());
        }
    }

    @Test
    void providerExposesPrimaryLookupAndEveryCandidateStreamed() {
        try (var context = new AnnotationConfigApplicationContext(PaymentConfig.class, LookupConfig.class)) {
            PaymentLookup lookup = context.getBean(PaymentLookup.class);

            assertEquals("card", lookup.availableProcessor().name());
            assertEquals("card", lookup.uniqueProcessor().name());
            assertEquals(3, lookup.processors().count());
        }
    }

    @Configuration(proxyBeanMethods = false)
    static class LookupConfig {

        @Bean
        PaymentLookup paymentLookup(ObjectProvider<PaymentProcessor> processors) {
            return new PaymentLookup(processors);
        }
    }
}
