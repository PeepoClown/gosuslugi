package com.example.project.controller;

import com.example.project.dto.EmployeeResponseDto;
import com.example.project.exception.NotFoundException;
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
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(
        value = "/employee",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class EmployeeController {
    private final EmployeeService employeeServiceImpl;

    @Autowired
    public EmployeeController(EmployeeService employeeServiceImpl) {
        this.employeeServiceImpl = employeeServiceImpl;
    }

    @GetMapping
    public ResponseEntity<?> getAllEmployees() {
        List<EmployeeResponseDto> dtoList = new ArrayList<>();
        employeeServiceImpl.getAll().forEach(employee -> {
            dtoList.add(EmployeeResponseDto.employeeToDto(employee));
        });
        return ResponseEntity.ok().body(new StatusWithData<>(HttpStatus.OK.value(), HttpStatus.OK.name(),
                "List of employees", dtoList));
    }

    @GetMapping("/{login}")
    public ResponseEntity<?> getEmployee(@PathVariable String login) throws NotFoundException {
        EmployeeResponseDto dto = EmployeeResponseDto.employeeToDto(employeeServiceImpl.getOne(login));
        return ResponseEntity.ok().body(new StatusWithData<>(HttpStatus.OK.value(), HttpStatus.OK.name(),
                "Employee info", dto));
    }

    @DeleteMapping("/{login}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String login) throws NotFoundException {
        employeeServiceImpl.delete(login);
        return ResponseEntity.ok().body(new Status(HttpStatus.OK.value(), HttpStatus.OK.name(),
                    String.format("Deleted employee %s", login)));
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handleClientException(HttpServletRequest request, NotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Status(HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleServerException(HttpServletRequest request, Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new Status(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.name(), ex.getMessage()));
    }
}
