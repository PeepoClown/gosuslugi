package com.example.project.service;

import com.example.project.dto.DeclarationRequestDto;
import com.example.project.entity.Declaration;
import com.example.project.exception.NotFoundException;

public interface DeclarationService {
    Declaration create(DeclarationRequestDto dto) throws NotFoundException;

    Iterable<Declaration> getAll();

    Declaration getOne(String number) throws NotFoundException;

    Declaration getByRequirements(String departmentCode, String declarationType, String number) throws NotFoundException;

    void delete(String number) throws NotFoundException;
}
