package com.example.stage01.plain;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * No container needed: because the clock is injected, a test can supply a fixed one
 * and assert the exact output.
 */
class BreakfastCookTest {

    @Test
    void servesAtTheTimeOnTheInjectedClock() {
        Clock eightOClock = Clock.fixed(Instant.parse("2026-01-01T08:00:00Z"), ZoneOffset.UTC);
        BreakfastCook cook = new BreakfastCook(new Pantry(), new Stove(), eightOClock);

        assertEquals("hot baked beans, served at 08:00:00", cook.cook());
    }
}
