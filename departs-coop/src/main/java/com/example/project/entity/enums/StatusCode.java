package com.example.project.entity.enums;

public enum StatusCode {
    WAIT("WAIT"),
    COMPLETED("COMPLETED");

    private String value;

    StatusCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
