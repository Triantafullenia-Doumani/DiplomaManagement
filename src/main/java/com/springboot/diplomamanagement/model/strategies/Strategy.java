package com.springboot.diplomamanagement.model.strategies;

public enum Strategy {
    BEST_GRADE("BEST_GRADE"),
    MINIMUM_COURSES_REMAINING("MINIMUM_COURSES_REMAINING"),
    RANDOM("RANDOM");

    private final String value;

    private Strategy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

