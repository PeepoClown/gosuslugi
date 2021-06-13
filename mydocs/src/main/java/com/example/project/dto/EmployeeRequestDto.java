package com.example.project.dto;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeRequestDto implements Serializable {
    private String initials;
    private String login;
    private String password;
}
