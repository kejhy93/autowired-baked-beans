package com.example.stage01.scan;

import org.springframework.stereotype.Component;

@Component
public class Stove {

    public String heat(String food) {
        return "hot " + food;
    }
}
