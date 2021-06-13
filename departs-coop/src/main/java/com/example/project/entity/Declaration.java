package com.example.project.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "seq_gen", sequenceName = "declaration_id_seq")
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
