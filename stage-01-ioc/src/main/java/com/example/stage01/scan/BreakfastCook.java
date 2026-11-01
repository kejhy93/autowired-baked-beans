package com.example.stage01.scan;

import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * No {@code @Autowired} is needed: a class with a single constructor uses it for injection.
 */
@Service
public class BreakfastCook {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final Pantry pantry;
    private final Stove stove;
    private final Clock clock;

    public BreakfastCook(Pantry pantry, Stove stove, Clock clock) {
        this.pantry = pantry;
        this.stove = stove;
        this.clock = clock;
    }

    public String cook() {
        String now = LocalTime.now(clock).format(TIME_FORMAT);
        return stove.heat(pantry.openCan()) + ", served at " + now;
    }
}
