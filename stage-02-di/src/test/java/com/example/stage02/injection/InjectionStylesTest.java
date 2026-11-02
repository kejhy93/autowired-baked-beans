package com.example.stage02.injection;

import com.example.stage02.payment.CardPaymentProcessor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InjectionStylesTest {

    @Test
    void constructorInjectionCanBeTestedWithNewAndHasARequiredDependency() {
        var checkout = new ConstructorInjectedCheckout(new CardPaymentProcessor());

        assertEquals("paid by card", checkout.checkout());
    }

    @Test
    void setterInjectionCanBeTestedWithNewAndCanRemainOptional() {
        var checkout = new SetterInjectedCheckout();
        assertEquals("no payment processor configured", checkout.checkout());

        checkout.setProcessor(new CardPaymentProcessor());
        assertEquals("paid by card", checkout.checkout());
    }

    @Test
    void fieldInjectionCanBeTestedWithoutAContainerButRequiresDirectFieldAccess() {
        var checkout = new FieldInjectedCheckout();
        checkout.processor = new CardPaymentProcessor();

        assertEquals("paid by card", checkout.checkout());
    }
}
