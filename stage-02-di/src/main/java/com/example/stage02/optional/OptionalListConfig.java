package com.example.stage02.optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/** Registers the optional field-injection example without any {@link ReceiptPrinter} beans. */
@Configuration(proxyBeanMethods = false)
@Import(OptionalReceiptPrinterList.class)
class OptionalListConfig {
}
