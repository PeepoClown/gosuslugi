package com.example.project.dto;

import com.example.project.entity.Declaration;
import com.example.project.util.LocalDateTimeDeserializer;
import com.example.project.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class DeclarationResponseDto {
    private String number;
    private String initials;
    private String passport;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createdAt;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime modifiedAt;
    private EmployeeResponseDto employee;
    private String type;
    private String department;
    private String status;

    public static DeclarationResponseDto declarationToDto(Declaration declaration) {
        return new DeclarationResponseDto(
                declaration.getNumber(),
                declaration.getClientInitials(),
                declaration.getPassport(),
                declaration.getCreateTime(),
                declaration.getModifiedTime(),
                EmployeeResponseDto.employeeToDto(declaration.getEmployee()),
                declaration.getType().getLabel(),
                declaration.getDepartment().getLabel(),
                declaration.getStatus().getLabel()
        );
    }
}
