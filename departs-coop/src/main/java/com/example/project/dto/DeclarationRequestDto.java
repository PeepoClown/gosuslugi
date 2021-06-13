package com.example.project.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DeclarationRequestDto implements Serializable {
    private String number;
    private String clientInitials;
    private String passport;
    private String type;
    private String department;
}
