package com.example.project.entity.enums;

import lombok.Getter;

@Getter
public enum DepartmentCode {
    DEP_PFR("DEP-PFR"),
    DEP_FNS("DEP-FNS");

    private final String value;

    DepartmentCode(String value) {
        this.value = value;
    }
}
