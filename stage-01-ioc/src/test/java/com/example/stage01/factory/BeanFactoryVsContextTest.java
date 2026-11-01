package com.example.stage01.factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BeanFactoryVsContextTest {

    @Test
    void bareFactoryIsLazyAndIgnoresAutowired() {
        var factory = BeanFactoryVsContextApp.bareFactory();

        assertFalse(factory.containsSingleton("probe"));
        assertFalse(factory.getBean(Probe.class).isWired());
        assertTrue(factory.containsSingleton("probe"));
    }

    @Test
    void annotationConfigContextIsEagerAndHonoursAutowired() {
        try (var context = BeanFactoryVsContextApp.refreshedContext()) {
            assertTrue(context.getBeanFactory().containsSingleton("probe"));
            assertTrue(context.getBean(Probe.class).isWired());
        }
    }
}
