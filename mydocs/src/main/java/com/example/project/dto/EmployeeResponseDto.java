package com.example.project.dto;

import com.example.project.entity.Employee;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeResponseDto implements Serializable {
    private String login;
    private String initials;

    public static EmployeeResponseDto employeeToDto(Employee employee) {
        return new EmployeeResponseDto(
                employee.getLogin(),
                employee.getInitials()
        );
    }
}
