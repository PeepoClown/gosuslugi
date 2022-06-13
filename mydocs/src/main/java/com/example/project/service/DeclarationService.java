package com.example.project.service;

import com.example.project.dto.DeclarationRequestDto;
import com.example.project.entity.Declaration;
import com.example.project.entity.enums.StatusCode;
import com.example.project.exception.NotFoundException;

public interface DeclarationService {

    Declaration create(DeclarationRequestDto dto) throws NotFoundException;

    Iterable<Declaration> getAll();

    Declaration getOne(String number) throws NotFoundException;

    Iterable<Declaration> getClientDeclarations(String passport) throws NotFoundException;

    String getByRequirements(String departmentCode, String declarationType, String number) throws NotFoundException;

    void delete(String number) throws NotFoundException;

    void complete(String number) throws NotFoundException;

    void updateStatus(Declaration declaration, StatusCode statusCode);
}
