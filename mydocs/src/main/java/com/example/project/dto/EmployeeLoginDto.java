package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeLoginDto {
    private String login;
    private String password;
}
