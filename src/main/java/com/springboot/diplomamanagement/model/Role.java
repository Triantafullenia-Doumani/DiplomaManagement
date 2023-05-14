package com.springboot.diplomamanagement.model;

public enum Role {
	USER("User"),
    PROFESSOR("Professor");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
