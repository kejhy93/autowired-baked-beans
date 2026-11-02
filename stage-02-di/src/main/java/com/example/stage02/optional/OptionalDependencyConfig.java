package com.example.stage02.optional;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

/** Configures a bean that demonstrates absent and present dependencies in several forms. */
@Configuration(proxyBeanMethods = false)
public class OptionalDependencyConfig {

    /**
     * Supplies Optional, nullable, collection, and deferred dependencies to the same demo object.
     * A collection parameter on a {@code @Bean} method becomes an empty list when there are no matches.
     */
    @Bean
    public OptionalDependencyDemo optionalDependencyDemo(Optional<ReceiptPrinter> optionalPrinter,
                                                         List<ReceiptPrinter> printers,
                                                         @Nullable ReceiptPrinter nullablePrinter,
                                                         ObjectProvider<ReceiptPrinter> provider) {
        return new OptionalDependencyDemo(optionalPrinter, printers, nullablePrinter, provider);
    }
}
