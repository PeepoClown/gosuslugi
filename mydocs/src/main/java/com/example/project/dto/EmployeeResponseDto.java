package com.example.project.dto;

import com.example.project.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmployeeResponseDto {
    private String login;
    private String initials;

    public static EmployeeResponseDto employeeToDto(Employee employee) {
        return new EmployeeResponseDto(
                employee.getLogin(),
                employee.getInitials()
        );
    }
}
