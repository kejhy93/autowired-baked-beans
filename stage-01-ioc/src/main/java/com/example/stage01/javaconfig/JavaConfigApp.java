package com.example.stage01.javaconfig;

import com.example.stage01.plain.BreakfastCook;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;

/**
 * Runs variant 2, which is wired by {@link KitchenConfig}.
 */
public class JavaConfigApp {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(KitchenConfig.class)) {
            BreakfastCook cook = context.getBean(BreakfastCook.class);
            System.out.println(cook.cook());

            // Includes Spring's own infrastructure beans, as well as yours
            Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out::println);
        }
    }
}
