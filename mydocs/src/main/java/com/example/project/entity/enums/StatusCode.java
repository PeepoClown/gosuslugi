package com.example.project.entity.enums;

public enum StatusCode {
    NEW("NEW"),
    PROCESSING("PROCESSING"),
    COMPLETED("COMPLETED");

    private String value;

    StatusCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
