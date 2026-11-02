package com.example.stage02.payment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Exercises the same wiring with and without compiler-retained parameter names. */
class ParameterNameMatchingTest {

    @Test
    void parameterNameSelectsBeanOnlyWhenCompilerRetainsParameterNames() throws NoSuchMethodException {
        boolean namesPresent = ParameterNameConfig.class
                .getDeclaredMethod("selection", PaymentProcessor.class)
                .getParameters()[0]
                .isNamePresent();
        var context = new AnnotationConfigApplicationContext();
        context.register(ParameterNameConfig.class);

        if (namesPresent) {
            try (context) {
                context.refresh();
                assertEquals("bank transfer", context.getBean(Selection.class).processor().name());
            }
        } else {
            try (context) {
                BeanCreationException exception = assertThrows(BeanCreationException.class, context::refresh);
                assertEquals(NoUniqueBeanDefinitionException.class, exception.getCause().getClass());
                assertTrue(exception.getCause().getMessage()
                        .contains("found 2: cardProcessor,bankTransferPaymentProcessor"));
            }
        }
    }

    @Configuration(proxyBeanMethods = false)
    static class ParameterNameConfig {

        @Bean
        PaymentProcessor cardProcessor() {
            return new CardPaymentProcessor();
        }

        @Bean
        PaymentProcessor bankTransferPaymentProcessor() {
            return new BankTransferPaymentProcessor();
        }

        @Bean
        Selection selection(PaymentProcessor bankTransferPaymentProcessor) {
            return new Selection(bankTransferPaymentProcessor);
        }
    }

    record Selection(PaymentProcessor processor) {
    }
}
