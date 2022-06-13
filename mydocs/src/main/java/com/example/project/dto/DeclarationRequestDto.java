package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeclarationRequestDto {
    private String initials;
    private String passport;
    private String employeeLogin;
    private String type;
}
