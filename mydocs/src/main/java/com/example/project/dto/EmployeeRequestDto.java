package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeRequestDto {
    private String initials;
    private String login;
    private String password;
}
