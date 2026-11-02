package com.example.stage02.optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.ObjectProvider;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.List;

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
