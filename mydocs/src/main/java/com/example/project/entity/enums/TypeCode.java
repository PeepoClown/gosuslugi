package com.example.project.entity.enums;

import lombok.Getter;

@Getter
public enum TypeCode {
    DOC_PFR("DOC-PFR"),
    DOC_FNS("DOC-FNS");

    private final String value;

    TypeCode(String value) {
        this.value = value;
    }
}
