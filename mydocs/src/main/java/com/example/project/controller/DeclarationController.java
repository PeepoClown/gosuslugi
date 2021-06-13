package com.example.project.controller;

import com.example.project.dto.DeclarationRequestDto;
import com.example.project.dto.DeclarationResponseDto;
import com.example.project.exception.NotFoundException;
import com.example.project.service.DeclarationService;
import com.example.project.service.impl.DeclarationServiceImpl;
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
        value = "/declaration",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class DeclarationController {
    private final DeclarationService declarationServiceImpl;

    @Autowired
    public DeclarationController(DeclarationService declarationServiceImpl) {
        this.declarationServiceImpl = declarationServiceImpl;
    }

    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestBody DeclarationRequestDto reqDto) throws NotFoundException {
        DeclarationResponseDto respDto = DeclarationResponseDto.declarationToDto(declarationServiceImpl.create(reqDto));
        return ResponseEntity.ok().body(new StatusWithData<>(HttpStatus.OK.value(), HttpStatus.OK.name(),
                    String.format("Declaration %s created", respDto.getNumber()), respDto));
    }

    @GetMapping
    public ResponseEntity<?> getAllDeclarations() {
        List<DeclarationResponseDto> dtoList = new ArrayList<>();
        declarationServiceImpl.getAll().forEach(declaration -> {
            dtoList.add(DeclarationResponseDto.declarationToDto(declaration));
        });
        return ResponseEntity.ok().body(new StatusWithData<>(HttpStatus.OK.value(), HttpStatus.OK.name(),
            "List of declarations", dtoList));
    }

    @GetMapping("/{number}")
    public ResponseEntity<?> getDeclaration(@PathVariable String number) throws NotFoundException {
        DeclarationResponseDto dto = DeclarationResponseDto.declarationToDto(declarationServiceImpl.getOne(number));
        return ResponseEntity.ok().body(new StatusWithData<>(HttpStatus.OK.value(), HttpStatus.OK.name(),
            "Declaration info", dto));
    }

    @GetMapping("/passport/{passport}")
    public ResponseEntity<?> getDeclarations(@PathVariable String passport) throws NotFoundException {
        List<DeclarationResponseDto> dtoList = new ArrayList<>();
        declarationServiceImpl.getClientDeclarations(passport).forEach(declaration -> {
            dtoList.add(DeclarationResponseDto.declarationToDto(declaration));
        });
        return ResponseEntity.ok().body(new StatusWithData<>(HttpStatus.OK.value(), HttpStatus.OK.name(),
                String.format("List of declarations by passport %s", passport), dtoList));
    }

    @GetMapping("/status")
    public ResponseEntity<?> getDeclarationByRequirements(@RequestParam(name = "department") String departmentCode,
                                                          @RequestParam(name = "type") String declarationType,
                                                          @RequestParam(name = "number") String number)
            throws NotFoundException {
        String status = declarationServiceImpl.getByRequirements(departmentCode, declarationType, number);
        return ResponseEntity.ok().body(new StatusWithData<>(HttpStatus.OK.value(), HttpStatus.OK.name(),
                String.format("Declaration %s status info", number), status));
    }

    @PutMapping("/status")
    public ResponseEntity<?> updateDeclaration(@RequestParam(name = "number") String number) throws NotFoundException {
        declarationServiceImpl.complete(number);
        return ResponseEntity.ok().body(new Status(HttpStatus.OK.value(), HttpStatus.OK.name(),
                String.format("Declaration %s completed", number)));
    }

    @DeleteMapping("/{number}")
    public ResponseEntity<?> deleteDeclaration(@PathVariable String number) throws NotFoundException {
        declarationServiceImpl.delete(number);
        return ResponseEntity.ok().body(new Status(HttpStatus.OK.value(), HttpStatus.OK.name(),
                String.format("Deleted declaration %s", number)));
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
