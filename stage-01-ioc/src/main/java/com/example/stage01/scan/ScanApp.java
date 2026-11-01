package com.example.stage01.scan;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * Runs variant 3, which is wired by {@link ScanConfig}.
 */
public class ScanApp {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(ScanConfig.class)) {
            BreakfastCook cook = context.getBean(BreakfastCook.class);
            System.out.println(cook.cook());

            // Includes Spring's own infrastructure beans, as well as yours
            Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
        }
    }
}
