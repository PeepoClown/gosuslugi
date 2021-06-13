package com.example.project.config;

import com.example.project.entity.Employee;
import com.example.project.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class AuthProvider implements AuthenticationProvider {
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthProvider(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken upAuth = (UsernamePasswordAuthenticationToken) authentication;
        String name = (String) authentication.getPrincipal();
        String password = (String) upAuth.getCredentials();
        Employee employee = employeeRepository.findByLogin(name);

        if (employee == null) {
            throw new BadCredentialsException("Incorrect login");
        }
        String storedPassword = employee.getPassword();
        if (!passwordEncoder.matches(password, storedPassword)) {
            throw new BadCredentialsException("Incorrect password");
        }

        Object principal = authentication.getPrincipal();
        UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
                principal, authentication.getCredentials(), Collections.emptyList());
        result.setDetails(authentication.getDetails());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
