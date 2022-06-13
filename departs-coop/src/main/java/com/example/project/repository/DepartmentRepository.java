package com.example.project.repository;

import com.example.project.entity.Department;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository
        extends CommonDirectoriesRepository<Department> {
}
