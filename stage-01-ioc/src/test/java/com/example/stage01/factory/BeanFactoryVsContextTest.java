package com.example.stage01.factory;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pins down the two differences that {@link BeanFactoryVsContextApp} prints: when the
 * singleton is created, and whether {@code @Autowired} is processed.
 */
class BeanFactoryVsContextTest {

    /**
     * The factory has only a definition until something asks for the bean, and it creates
     * the bean without calling the {@code @Autowired} setter.
     */
    @Test
    void bareFactoryIsLazyAndIgnoresAutowired() {
        var factory = BeanFactoryVsContextApp.bareFactory();

        assertFalse(factory.containsSingleton("probe"));
        assertFalse(factory.getBean(Probe.class).isWired());
        assertTrue(factory.containsSingleton("probe"));
    }

    /**
     * The singleton already exists when {@code refresh()} returns, before any {@code getBean}
     * call, and its {@code @Autowired} setter has been called.
     */
    @Test
    void annotationConfigContextIsEagerAndHonoursAutowired() {
        try (var context = BeanFactoryVsContextApp.refreshedContext()) {
            assertTrue(context.getBeanFactory().containsSingleton("probe"));
            assertTrue(context.getBean(Probe.class).isWired());
        }
    }
}
