package com.example.project.entity.enums;

public enum TypeCode {
    DOC_PFR("DOC-PFR"),
    DOC_FNS("DOC-FNS");

    private String value;

    TypeCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
