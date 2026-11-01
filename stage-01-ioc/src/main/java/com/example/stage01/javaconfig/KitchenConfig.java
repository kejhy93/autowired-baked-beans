package com.example.stage01.javaconfig;

import com.example.stage01.plain.BreakfastCook;
import com.example.stage01.plain.Pantry;
import com.example.stage01.plain.Stove;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

/**
 * Variant 2: explicit {@code @Bean} methods. The classes stay free of Spring, so this
 * style also works for classes you can't annotate, such as {@link Clock}.
 * Method parameters are the dependencies. Spring resolves them by type, and if several beans
 * match, it can fall back to the parameter name when compiled with {@code -parameters}.
 */
@Configuration
public class KitchenConfig {

    @Bean
    public Pantry pantry() {
        return new Pantry();
    }

    @Bean
    public Stove stove() {
        return new Stove();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    public BreakfastCook breakfastCook(Pantry pantry, Stove stove, Clock clock) {
        return new BreakfastCook(pantry, stove, clock);
    }
}
