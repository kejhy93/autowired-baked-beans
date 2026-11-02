package com.example.stage02.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

class QualifierFailureTest {

    @Test
    void qualifierWithNoMatchingCandidateFailsContextStartup() {
        var context = new AnnotationConfigApplicationContext();
        context.register(MissingQualifierConfig.class);

        try (context) {
            BeanCreationException exception = assertThrows(BeanCreationException.class, context::refresh);
            Throwable cause = exception;
            while (cause.getCause() != null) {
                cause = cause.getCause();
            }
            assertInstanceOf(NoSuchBeanDefinitionException.class, cause);
        }
    }

    @Configuration(proxyBeanMethods = false)
    @Import({CardPaymentProcessor.class, BankTransferPaymentProcessor.class, VoucherPaymentProcessor.class})
    static class MissingQualifierConfig {

        @Bean
        String selection(@Qualifier("notAProcessor") PaymentProcessor processor) {
            return processor.name();
        }
    }
}
