package com.springboot.diplomamanagement.model;

public enum ApplicationStatus {
    PENDING("Pending"),
    ACCEPTED("Accepted"),
    GRADED("Graded"),

    REJECTED("Rejected");

    private final String value;

    private ApplicationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
