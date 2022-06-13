package com.example.project.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(allocationSize = 1, name = "seq_gen", sequenceName = "declaration_id_seq")
@Table(name = "declaration")
public class Declaration extends AbstractEntity {
    @Column(name = "number", unique = true, nullable = false)
    private String number;

    @Column(name = "client_initials", nullable = false)
    private String clientInitials;

    @Column(name = "passport", nullable = false)
    private String passport;

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private Type type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;
}
