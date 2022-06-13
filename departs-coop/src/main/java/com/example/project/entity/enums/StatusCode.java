package com.example.project.entity.enums;

import lombok.Getter;

@Getter
public enum StatusCode {
    WAIT("WAIT"),
    COMPLETED("COMPLETED");

    private final String value;

    StatusCode(String value) {
        this.value = value;
    }
}
