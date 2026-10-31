package com.example.stage01.factory;

import org.springframework.beans.factory.annotation.Autowired;

import java.time.Clock;

/**
 * Reports when it is created and whether {@code @Autowired} was honoured.
 * Uses setter injection because a bare BeanFactory wouldn't resolve a constructor argument.
 */
public class Probe {

    private Clock clock;

    public Probe() {
        System.out.println("  -> Probe constructor called");
    }

    @Autowired
    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public boolean isWired() {
        return clock != null;
    }
}
