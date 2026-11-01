package com.example.stage01.scan;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

/**
 * Variant 3: component scanning. With no arguments, {@code @ComponentScan} scans this
 * class's package and its subpackages. {@link Clock} still needs a {@code @Bean} method,
 * because you can't add {@code @Component} to a JDK class.
 */
@Configuration
@ComponentScan
public class ScanConfig {

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
