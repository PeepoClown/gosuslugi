package com.example.project.entity.enums;

public enum DepartmentCode {
    DEP_PFR("DEP-PFR"),
    DEP_FNS("DEP-FNS");

    private String value;

    DepartmentCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
