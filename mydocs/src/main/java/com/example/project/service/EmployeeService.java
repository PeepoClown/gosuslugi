package com.example.project.service;

import com.example.project.dto.EmployeeLoginDto;
import com.example.project.dto.EmployeeRequestDto;
import com.example.project.entity.Employee;
import com.example.project.exception.AlreadyExistException;
import com.example.project.exception.IncorrectCredentialsException;
import com.example.project.exception.NotFoundException;

public interface EmployeeService {
    Employee create(EmployeeRequestDto dto) throws AlreadyExistException;
    void authenticate(EmployeeLoginDto dto) throws IncorrectCredentialsException;
    Iterable<Employee> getAll();
    Employee getOne(String login) throws NotFoundException;
    void delete(String login) throws NotFoundException;
}
