package com.example.project.service.impl;

import com.example.project.dto.DeclarationRequestDto;
import com.example.project.entity.Declaration;
import com.example.project.entity.Department;
import com.example.project.entity.Type;
import com.example.project.exception.NotFoundException;
import com.example.project.repository.DeclarationRepository;
import com.example.project.repository.DepartmentRepository;
import com.example.project.repository.StatusRepository;
import com.example.project.repository.TypeRepository;
import com.example.project.service.DeclarationService;
import com.example.project.util.status.StatusWithData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:application.properties")
public class DeclarationServiceImpl implements DeclarationService {
    private static final Logger logger = Logger.getLogger(DeclarationServiceImpl.class);
    private static final Long SECONDS_TO_PROCESS = 2L * 60 - 1;
    private static final String PROP_MYDOCS_URL = "mydocs.url.getstatus";
    private static final String PROP_USER_NAME = "scheduler.user";
    private static final String PROP_USER_PASSWORD = "scheduler.password";

    private final DeclarationRepository declarationRepository;
    private final TypeRepository typeRepository;
    private final DepartmentRepository departmentRepository;
    private final StatusRepository statusRepository;
    private final RestTemplate restTemplate;
    private final Environment env;

    @Autowired
    public DeclarationServiceImpl(DeclarationRepository declarationRepository, TypeRepository typeRepository,
                                  DepartmentRepository departmentRepository, StatusRepository statusRepository,
                                  RestTemplate restTemplate, Environment env) {
        this.declarationRepository = declarationRepository;
        this.typeRepository = typeRepository;
        this.departmentRepository = departmentRepository;
        this.statusRepository = statusRepository;
        this.restTemplate = restTemplate;
        this.env = env;
    }

    @Transactional
    public Declaration create(DeclarationRequestDto dto) throws NotFoundException {
        Declaration declaration = new Declaration();
        declaration.setNumber(dto.getNumber());
        declaration.setClientInitials(dto.getClientInitials());
        declaration.setPassport(dto.getPassport());
        Type type = typeRepository.findByCode(dto.getType());
        if (type == null) {
            throw new NotFoundException(String.format("%s is unsupported type code of declaration", dto.getType()));
        }
        declaration.setType(type);
        Department department = departmentRepository.findByCode(dto.getDepartment());
        if (department == null) {
            throw new NotFoundException(String.format("%s is unsupported department code of declaration", dto.getDepartment()));
        }
        declaration.setDepartment(department);
        declaration.setStatus(statusRepository.findByCode("WAIT"));
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
    public Declaration getByRequirements(String departmentCode, String declarationType, String number) throws NotFoundException {
        Department department = departmentRepository.findByCode(departmentCode);
        Type type = typeRepository.findByCode(declarationType);
        if (department == null || type == null ||
                declarationRepository.findByRequirements(department, type, number) == null) {
            throw new NotFoundException(
                    String.format("Declaration with type %s to department %s and number %s not found",
                            declarationType, declarationType, number)
            );
        }
        return declarationRepository.findByRequirements(department, type, number);
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
    @Scheduled(cron = "0 * * * * MON-SUN")
    public void processDeclaration() {
        List<Declaration> newDeclarations = new ArrayList<>(declarationRepository
                .findAllByStatus(statusRepository.findByCode("WAIT")));
        if (newDeclarations.isEmpty()) {
            return;
        }
        Declaration declaration = null;
        for (Declaration decl : newDeclarations) {
            if (Duration.between(decl.getCreateTime(), LocalDateTime.now()).toSeconds() >= SECONDS_TO_PROCESS) {
                declaration = decl;
                break;
            }
        }
        if (declaration == null) {
            return;
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBasicAuth(
                    env.getRequiredProperty(PROP_USER_NAME),
                    env.getRequiredProperty(PROP_USER_PASSWORD)
            );
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(env.getRequiredProperty(PROP_MYDOCS_URL))
                    .queryParam("number", declaration.getNumber());
            ResponseEntity<StatusWithData> retStatus = restTemplate.exchange(builder.toUriString(), HttpMethod.PUT,
                    new HttpEntity<>(headers), StatusWithData.class);
            logger.info("Mydocs returned status: " + retStatus);

            declaration.setStatus(statusRepository.findByCode("COMPLETED"));
            declarationRepository.save(declaration);
        } catch (Exception ex) {
            logger.debug("Exception in scheduler bot: " + ex.getMessage());
        }
    }
}
