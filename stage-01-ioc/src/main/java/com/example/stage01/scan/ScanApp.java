package com.example.stage01.scan;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ScanApp {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(ScanConfig.class)) {
            System.out.println(context.getBean(BreakfastCook.class).cook());
        }
    }
}
