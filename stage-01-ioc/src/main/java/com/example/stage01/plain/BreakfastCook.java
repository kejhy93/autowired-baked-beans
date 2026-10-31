package com.example.stage01.plain;

import java.time.Clock;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Has no Spring imports. It only declares what it needs, and whoever creates it
 * supplies those things. That is dependency injection, with or without a container.
 */
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
