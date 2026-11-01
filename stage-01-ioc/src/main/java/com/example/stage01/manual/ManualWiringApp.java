package com.example.stage01.manual;

import com.example.stage01.plain.BreakfastCook;
import com.example.stage01.plain.Pantry;
import com.example.stage01.plain.Stove;

import java.time.Clock;

/**
 * Variant 1: no Spring. This method decides what gets created, in what order,
 * and who shares what. Spring takes over exactly this job.
 */
public class ManualWiringApp {

    public static void main(String[] args) {
        Pantry pantry = new Pantry();
        Stove stove = new Stove();
        Clock clock = Clock.systemDefaultZone();
        BreakfastCook cook = new BreakfastCook(pantry, stove, clock);

        System.out.println(cook.cook());
    }
}
