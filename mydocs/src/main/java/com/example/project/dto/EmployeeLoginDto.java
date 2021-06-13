package com.example.project.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeLoginDto implements Serializable {
    private String login;
    private String password;
}
