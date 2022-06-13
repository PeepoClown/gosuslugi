package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class DeclarationRequestDto implements Serializable {
    private String number;
    private String clientInitials;
    private String passport;
    private String type;
    private String department;
}
