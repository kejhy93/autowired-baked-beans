package com.example.stage02.optional;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OptionalDependenciesTest {

    @Test
    void optionalFormsAcceptAnAbsentDependency() {
        try (var context = new AnnotationConfigApplicationContext(OptionalDependencyConfig.class)) {
            OptionalDependencyDemo demo = context.getBean(OptionalDependencyDemo.class);

            assertTrue(demo.optionalPrinter().isEmpty());
            assertTrue(demo.printers().isEmpty());
            assertNull(demo.nullablePrinter());
            assertNull(demo.setterPrinter());
            assertNull(demo.provider().getIfAvailable());
            assertNull(demo.provider().getIfUnique());
            assertFalse(demo.provider().stream().findAny().isPresent());
        }
    }

    @Test
    void optionalFormsReceiveTheDependencyWhenOneIsAvailable() {
        try (var context = new AnnotationConfigApplicationContext(OptionalDependencyWithPrinterConfig.class)) {
            OptionalDependencyDemo demo = context.getBean(OptionalDependencyDemo.class);

            assertEquals("paper receipt", demo.optionalPrinter().orElseThrow().print());
            assertEquals(1, demo.printers().size());
            assertEquals("paper receipt", demo.nullablePrinter().print());
            assertEquals("paper receipt", demo.setterPrinter().print());
            assertEquals("paper receipt", demo.provider().getIfAvailable().print());
            assertEquals("paper receipt", demo.provider().getIfUnique().print());
            assertEquals(1, demo.provider().stream().count());
        }
    }
}
