package com.example.project.controller;

import com.example.project.dto.EmployeeLoginDto;
import com.example.project.dto.EmployeeResponseDto;
import com.example.project.dto.EmployeeRequestDto;
import com.example.project.exception.AlreadyExistException;
import com.example.project.exception.IncorrectCredentialsException;
import com.example.project.service.EmployeeService;
import com.example.project.service.impl.EmployeeServiceImpl;
import com.example.project.util.status.Status;
import com.example.project.util.status.StatusWithData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(
        value = "/",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AuthorizationController {
    private final EmployeeService employeeServiceImpl;

    @Autowired
    public AuthorizationController(EmployeeService employeeServiceImpl) {
        this.employeeServiceImpl = employeeServiceImpl;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody EmployeeRequestDto reqDto) throws AlreadyExistException {
        EmployeeResponseDto respDto = EmployeeResponseDto.employeeToDto(employeeServiceImpl.create(reqDto));
        return ResponseEntity.ok().body(new StatusWithData<>(HttpStatus.OK.value(), HttpStatus.OK.name(),
                    String.format("Employee %s created", respDto.getLogin()), respDto));
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authentication(@RequestBody EmployeeLoginDto dto) throws IncorrectCredentialsException {
        employeeServiceImpl.authenticate(dto);
        return ResponseEntity.ok().body(new Status(HttpStatus.OK.value(), HttpStatus.OK.name(),
                String.format("Employee %s authenticated", dto.getLogin())));
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<?> handleClientException(HttpServletRequest request, AlreadyExistException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Status(HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(), ex.getMessage()));
    }

    @ExceptionHandler(IncorrectCredentialsException.class)
    public ResponseEntity<?> handleClientException(HttpServletRequest request, IncorrectCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Status(HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.name(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleServerException(HttpServletRequest request, Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Status(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage()));
    }
}
