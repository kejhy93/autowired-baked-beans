package com.example.stage02;

import com.example.stage02.payment.PaymentConfig;
import com.example.stage02.payment.PaymentRouter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.stream.Collectors;

public class DependencyInjectionApp {

    public static void main(String[] args) {
        try (var context = new AnnotationConfigApplicationContext(PaymentConfig.class)) {
            PaymentRouter router = context.getBean(PaymentRouter.class);
            String processorNames = router.allProcessors().stream()
                    .map(processor -> processor.name())
                    .collect(Collectors.joining(", "));

            System.out.println("Default processor: " + router.defaultProcessor().name());
            System.out.println("Bank rail: " + router.bankRailProcessor().name());
            System.out.println("All processors: " + processorNames);
            System.out.println("Runtime route: " + router.route("voucherPaymentProcessor"));
        }
    }
}
