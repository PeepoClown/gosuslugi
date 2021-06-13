package com.example.project.service.impl;

import com.example.project.dto.EmployeeLoginDto;
import com.example.project.dto.EmployeeRequestDto;
import com.example.project.entity.Employee;
import com.example.project.exception.AlreadyExistException;
import com.example.project.exception.IncorrectCredentialsException;
import com.example.project.exception.NotFoundException;
import com.example.project.repository.EmployeeRepository;
import com.example.project.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Employee create(EmployeeRequestDto dto) throws AlreadyExistException {
        if (employeeRepository.findByLogin(dto.getLogin()) != null) {
            throw new AlreadyExistException(String.format("Employee with login %s already exist", dto.getLogin()));
        }
        Employee employee = new Employee();
        employee.setLogin(dto.getLogin());
        employee.setPassword(passwordEncoder.encode(dto.getPassword()));
        employee.setInitials(dto.getInitials());
        return employeeRepository.save(employee);
    }

    @Transactional(readOnly = true)
    public void authenticate(EmployeeLoginDto dto) throws IncorrectCredentialsException {
        Employee employee = employeeRepository.findByLogin(dto.getLogin());
        if (employee == null) {
            throw new IncorrectCredentialsException(String.format("Employee with login %s not found", dto.getLogin()));
        }
        if (!passwordEncoder.matches(dto.getPassword(), employee.getPassword())) {
            throw new IncorrectCredentialsException("Incorrect password");
        }
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(
                dto.getLogin(),
                dto.getPassword()
        ));
    }

    @Transactional(readOnly = true)
    public Iterable<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Employee getOne(String login) throws NotFoundException {
        if (employeeRepository.findByLogin(login) == null) {
            throw new NotFoundException(String.format("Employee with login %s not found", login));
        }
        return employeeRepository.findByLogin(login);
    }

    @Transactional
    public void delete(String login) throws NotFoundException {
        Employee employee = employeeRepository.findByLogin(login);
        if (employee == null) {
            throw new NotFoundException(String.format("Employee with login %s not found", login));
        }
        employeeRepository.deleteById(employee.getId());
    }
}
