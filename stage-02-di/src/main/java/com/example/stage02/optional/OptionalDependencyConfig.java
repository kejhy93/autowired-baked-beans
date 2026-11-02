package com.example.stage02.optional;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration(proxyBeanMethods = false)
public class OptionalDependencyConfig {

    @Bean
    public OptionalDependencyDemo optionalDependencyDemo(Optional<ReceiptPrinter> optionalPrinter,
                                                         List<ReceiptPrinter> printers,
                                                         @Nullable ReceiptPrinter nullablePrinter,
                                                         ObjectProvider<ReceiptPrinter> provider) {
        return new OptionalDependencyDemo(optionalPrinter, printers, nullablePrinter, provider);
    }
}
