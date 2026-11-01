package com.example.stage01;

import com.example.stage01.javaconfig.KitchenConfig;
import com.example.stage01.plain.BreakfastCook;
import com.example.stage01.scan.ScanConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WiringTest {

    private static final Clock EIGHT_O_CLOCK = Clock.fixed(Instant.parse("2026-01-01T08:00:00Z"), ZoneOffset.UTC);

    @Test
    void javaConfigWiresTheCook() {
        try (var context = contextAtEightOClock(KitchenConfig.class)) {
            assertEquals("hot baked beans, served at 08:00:00", context.getBean(BreakfastCook.class).cook());
        }
    }

    @Test
    void componentScanWiresTheCook() {
        try (var context = contextAtEightOClock(ScanConfig.class)) {
            assertEquals("hot baked beans, served at 08:00:00",
                    context.getBean(com.example.stage01.scan.BreakfastCook.class).cook());
        }
    }

    @Test
    void beansAreSingletonsByDefault() {
        try (var context = new AnnotationConfigApplicationContext(KitchenConfig.class)) {
            assertSame(context.getBean(BreakfastCook.class), context.getBean(BreakfastCook.class));
        }
    }

    @Test
    void scanOnlyCoversTheConfigPackage() {
        try (var context = new AnnotationConfigApplicationContext(ScanConfig.class)) {
            assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(KitchenConfig.class));
        }
    }

    /**
     * Replaces the config's own clock instead of adding a second one, so these tests
     * still fail if the config stops defining a clock.
     */
    private static AnnotationConfigApplicationContext contextAtEightOClock(Class<?> config) {
        var context = new AnnotationConfigApplicationContext();
        context.register(config);
        context.getBeanFactory().addBeanPostProcessor(new BeanPostProcessor() {
            @Override
            public Object postProcessAfterInitialization(Object bean, String beanName) {
                return bean instanceof Clock ? EIGHT_O_CLOCK : bean;
            }
        });
        context.refresh();
        return context;
    }
}
