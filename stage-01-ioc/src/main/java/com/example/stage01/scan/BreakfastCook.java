package com.example.stage01.scan;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * No {@code @Autowired} is needed: a class with a single constructor uses it for injection.
 */
@Service
public class BreakfastCook {

    private final Pantry pantry;
    private final Stove stove;
    private final Clock clock;

    public BreakfastCook(Pantry pantry, Stove stove, Clock clock) {
        this.pantry = pantry;
        this.stove = stove;
        this.clock = clock;
    }

    public String cook() {
        LocalTime now = LocalTime.now(clock).truncatedTo(ChronoUnit.SECONDS);
        return stove.heat(pantry.openCan()) + ", served at " + now;
    }
}
