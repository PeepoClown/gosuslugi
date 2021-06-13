package com.example.project.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DeclarationRequestDto implements Serializable {
    private String initials;
    private String passport;
    private String employeeLogin;
    private String type;
}
