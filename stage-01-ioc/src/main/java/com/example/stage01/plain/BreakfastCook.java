package com.example.stage01.plain;

import java.time.Clock;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Has no Spring imports. It only declares what it needs, and whoever creates it
 * supplies those things. That is dependency injection, with or without a container.
 */
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
