package com.example.project.entity.enums;

import lombok.Getter;

@Getter
public enum StatusCode {
    NEW("NEW"),
    PROCESSING("PROCESSING"),
    COMPLETED("COMPLETED");

    private final String value;

    StatusCode(String value) {
        this.value = value;
    }
}
