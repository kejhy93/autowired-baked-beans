package com.example.stage02.optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/** Registers a required list injection with no candidates, so context refresh must fail. */
@Configuration(proxyBeanMethods = false)
@Import(RequiredReceiptPrinterList.class)
class RequiredListConfig {
}
