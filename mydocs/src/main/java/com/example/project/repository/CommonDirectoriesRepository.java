package com.example.project.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CommonDirectoriesRepository<E> extends CrudRepository<E, Long> {

    E findByCode(String code);
    E findByLabel(String label);
}
