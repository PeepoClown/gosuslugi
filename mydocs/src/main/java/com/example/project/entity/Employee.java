package com.example.project.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@SequenceGenerator(initialValue = 1, allocationSize = 1, name = "seq_gen", sequenceName = "employee_id_seq")
@Table(name = "employee")
public class Employee extends AbstractEntity {
    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "initials", nullable = false)
    private String initials;
}
