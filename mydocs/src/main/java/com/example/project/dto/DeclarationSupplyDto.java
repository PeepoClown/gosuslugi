package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeclarationSupplyDto {
    private String number;
    private String clientInitials;
    private String passport;
    private String type;
    private String department;
}
