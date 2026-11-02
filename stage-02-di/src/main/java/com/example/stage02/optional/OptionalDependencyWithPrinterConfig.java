package com.example.stage02.optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/** Adds one concrete printer to the optional-dependency configuration for the populated case. */
@Configuration(proxyBeanMethods = false)
@Import({OptionalDependencyConfig.class, PaperReceiptPrinter.class})
class OptionalDependencyWithPrinterConfig {
}
