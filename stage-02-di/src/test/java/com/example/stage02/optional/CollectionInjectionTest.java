package com.example.stage02.optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CollectionInjectionTest {

    @Test
    void requiredCollectionInjectionFailsWhenThereAreNoCandidates() {
        var context = new AnnotationConfigApplicationContext();
        context.register(RequiredListConfig.class);

        try (context) {
            assertThrows(UnsatisfiedDependencyException.class, context::refresh);
        }
    }

    @Test
    void nonRequiredCollectionInjectionLeavesTheFieldUnsetWhenThereAreNoCandidates() {
        try (var context = new AnnotationConfigApplicationContext(OptionalListConfig.class)) {
            assertNull(context.getBean(OptionalReceiptPrinterList.class).printers());
        }
    }
}
