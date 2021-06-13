package com.example.project.service.impl;

import com.example.project.dto.DeclarationRequestDto;
import com.example.project.dto.DeclarationSupplyDto;
import com.example.project.entity.Declaration;
import com.example.project.entity.Department;
import com.example.project.entity.Employee;
import com.example.project.entity.Type;
import com.example.project.entity.enums.StatusCode;
import com.example.project.entity.enums.TypeCode;
import com.example.project.exception.NotFoundException;
import com.example.project.repository.*;
import com.example.project.service.DeclarationService;
import com.example.project.util.status.Status;
import com.example.project.util.status.StatusWithData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class DeclarationServiceImpl implements DeclarationService {
    private static final Logger logger = Logger.getLogger(DeclarationServiceImpl.class);
    private static final String PROP_DEPARTS_COOP_URL = "departs.coop.url.new";
    private static final String PROP_DEPARTS_COOP_STATUS_URL = "departs.coop.url.status";

    private final DeclarationRepository declarationRepository;
    private final EmployeeRepository employeeRepository;
    private final TypeRepository typeRepository;
    private final DepartmentRepository departmentRepository;
    private final StatusRepository statusRepository;
    private final RestTemplate restTemplate;
    private final Environment env;

    @Autowired
    public DeclarationServiceImpl(DeclarationRepository declarationRepository, EmployeeRepository employeeRepository,
                                  TypeRepository typeRepository, DepartmentRepository departmentRepository,
                                  StatusRepository statusRepository, RestTemplate restTemplate, Environment env) {
        this.declarationRepository = declarationRepository;
        this.employeeRepository = employeeRepository;
        this.typeRepository = typeRepository;
        this.departmentRepository = departmentRepository;
        this.statusRepository = statusRepository;
        this.restTemplate = restTemplate;
        this.env = env;
    }

    @Transactional
    public Declaration create(DeclarationRequestDto dto) throws NotFoundException {
        Employee employee = employeeRepository.findByLogin(dto.getEmployeeLogin()); // get from security context holder ?
        Type type = typeRepository.findByLabel(dto.getType());
        if (employee == null) {
            throw new NotFoundException(String.format("Employee with login %s not found", dto.getEmployeeLogin()));
        }
        if (type == null) {
            throw new NotFoundException(String.format("%s is unsupported type of declaration", dto.getType()));
        }
        Declaration declaration = new Declaration();
        String number = Declaration.generateNumber();
        while (declarationRepository.findByNumber(number) != null) {
            number = Declaration.generateNumber();
        }
        declaration.setNumber(number);
        declaration.setClientInitials(dto.getInitials());
        declaration.setPassport(dto.getPassport());
        declaration.setEmployee(employee);
        declaration.setType(type);
        declaration.setDepartment(typeToDepartment(type));
        declaration.setStatus(statusRepository.findByCode("NEW"));
        return declarationRepository.save(declaration);
    }

    @Transactional(readOnly = true)
    public Iterable<Declaration> getAll() {
        return declarationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Declaration getOne(String number) throws NotFoundException {
        if (declarationRepository.findByNumber(number) == null) {
            throw new NotFoundException(String.format("Declaration with number %s not found", number));
        }
        return declarationRepository.findByNumber(number);
    }

    @Transactional(readOnly = true)
    public Iterable<Declaration> getClientDeclarations(String passport) throws NotFoundException {
        Collection<Declaration> declarations = declarationRepository.findAllByPassport(passport);
        if (declarations.isEmpty()) {
            throw new NotFoundException(String.format("Client with passport %s not found", passport));
        }
        return declarations;
    }

    @Transactional(readOnly = true)
    public String getByRequirements(String departmentCode, String declarationType, String number) throws NotFoundException {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(env.getRequiredProperty(PROP_DEPARTS_COOP_STATUS_URL))
                    .queryParam("department", departmentCode)
                    .queryParam("type", declarationType)
                    .queryParam("number", number);
            ResponseEntity<StatusWithData> retStatus = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,
                    new HttpEntity<>(headers), StatusWithData.class);
            logger.info("Departs-coop returned status: " + retStatus);
            return (String) retStatus.getBody().getData();
        } catch (Exception ex) {
            throw new NotFoundException(String.format("Declaration with type %s to department %s and number %s" +
                    " not registered yet", declarationType, declarationType, number));
        }
    }

    @Transactional
    public void delete(String number) throws NotFoundException {
        Declaration declaration = declarationRepository.findByNumber(number);
        if (declaration == null) {
            throw new NotFoundException(String.format("Declaration with number %s not found", number));
        }
        declarationRepository.deleteById(declaration.getId());
    }

    @Transactional
    public void complete(String number) throws NotFoundException {
        Declaration declaration = declarationRepository.findByNumber(number);
        if (declaration == null) {
            throw new NotFoundException(String.format("Declaration with number %s not found", number));
        }
        updateStatus(declaration, StatusCode.COMPLETED);
        declarationRepository.save(declaration);
    }

    @Transactional
    @Scheduled(cron = "0 * * * * MON-SUN")
    public void transferDeclarations() {
        List<Declaration> newDeclarations = new ArrayList<>(declarationRepository
                .findAllByStatus(statusRepository.findByCode("NEW")));
        if (newDeclarations.isEmpty()) {
            return;
        }
        Declaration declaration = newDeclarations.get(0);
        DeclarationSupplyDto dto = new DeclarationSupplyDto(
                declaration.getNumber(),
                declaration.getClientInitials(),
                declaration.getPassport(),
                declaration.getType().getCode().getValue(),
                declaration.getDepartment().getCode().getValue()
        );

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            ResponseEntity<Status> retStatus = restTemplate.exchange(env.getRequiredProperty(PROP_DEPARTS_COOP_URL), HttpMethod.POST,
                    new HttpEntity<>(dto, headers), Status.class);
            logger.info("Departs-coop returned status: " + retStatus);

            updateStatus(declaration, StatusCode.PROCESSING);
            declarationRepository.save(declaration);
        } catch (Exception ex) {
            logger.debug("Exception in scheduler bot: " + ex.getMessage());
        }
    }

    public void updateStatus(Declaration declaration, StatusCode statusCode) {
        declaration.setStatus(statusRepository.findByCode(statusCode.getValue()));
    }

    private Department typeToDepartment(Type type) {
        return type.getCode().equals(TypeCode.DOC_PFR)
                ? departmentRepository.findByCode("DEP-PFR")
                : departmentRepository.findByCode("DEP-FNS");
    }
}
