package com.example.stage02.optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration(proxyBeanMethods = false)
@Import(OptionalReceiptPrinterList.class)
class OptionalListConfig {
}
