package com.example.project.entity;

import com.example.project.entity.enums.DepartmentCode;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "label")
    private String label;

    public DepartmentCode getCode() {
        return code.equals("DEP-PFR") ? DepartmentCode.DEP_PFR : DepartmentCode.DEP_FNS;
    }
}
