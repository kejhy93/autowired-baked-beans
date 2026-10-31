package com.example.stage01;

import com.example.stage01.javaconfig.KitchenConfig;
import com.example.stage01.plain.BreakfastCook;
import com.example.stage01.scan.ScanConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WiringTest {

    @Test
    void javaConfigWiresTheCook() {
        try (var context = new AnnotationConfigApplicationContext(KitchenConfig.class)) {
            String breakfast = context.getBean(BreakfastCook.class).cook();
            assertTrue(breakfast.startsWith("hot baked beans"), breakfast);
        }
    }

    @Test
    void componentScanWiresTheCook() {
        try (var context = new AnnotationConfigApplicationContext(ScanConfig.class)) {
            String breakfast = context.getBean(com.example.stage01.scan.BreakfastCook.class).cook();
            assertTrue(breakfast.startsWith("hot baked beans"), breakfast);
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
}
