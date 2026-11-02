package com.example.stage02;

import com.example.stage02.payment.CardPaymentProcessor;
import com.example.stage02.payment.FallbackOnlyPaymentConfig;
import com.example.stage02.payment.PaymentConfig;
import com.example.stage02.payment.PaymentRouter;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/** Verifies single-candidate resolution, collection injection, and runtime routing. */
class CandidateResolutionTest {

    @Test
    void primaryWinsWhenSeveralProcessorsMatchByType() {
        try (var context = new AnnotationConfigApplicationContext(PaymentConfig.class)) {
            PaymentRouter router = context.getBean(PaymentRouter.class);

            assertSame(context.getBean(CardPaymentProcessor.class), router.defaultProcessor());
            assertEquals("card", router.defaultProcessor().name());
        }
    }

    @Test
    void qualifierSelectsOneCandidateWithoutChangingThePrimary() {
        try (var context = new AnnotationConfigApplicationContext(PaymentConfig.class)) {
            PaymentRouter router = context.getBean(PaymentRouter.class);

            assertEquals("card", router.defaultProcessor().name());
            assertEquals("bank transfer", router.bankTransferProcessor().name());
            assertEquals("bank transfer", router.bankRailProcessor().name());
        }
    }

    @Test
    void primaryDoesNotRemoveCandidatesFromCollections() {
        try (var context = new AnnotationConfigApplicationContext(PaymentConfig.class)) {
            PaymentRouter router = context.getBean(PaymentRouter.class);

            assertEquals(3, router.allProcessors().size());
            assertEquals(3, router.processorsByBeanName().size());
            assertEquals("card", router.allProcessors().getFirst().name());
            assertEquals("card", router.processorsByBeanName().get("cardPaymentProcessor").name());
        }
    }

    @Test
    void routerUsesTheBeanNameMapForRuntimeSelection() {
        try (var context = new AnnotationConfigApplicationContext(PaymentConfig.class)) {
            PaymentRouter router = context.getBean(PaymentRouter.class);

            assertEquals("paid by bank transfer", router.route("bankTransferPaymentProcessor"));
        }
    }

    @Test
    void fallbackIsUsedOnlyWhenNoRegularCandidateExists() {
        try (var context = new AnnotationConfigApplicationContext(FallbackOnlyPaymentConfig.class)) {
            assertEquals("voucher", context.getBean(PaymentRouter.class).defaultProcessor().name());
        }
    }
}
