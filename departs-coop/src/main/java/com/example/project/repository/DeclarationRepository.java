package com.example.project.repository;

import com.example.project.entity.Declaration;
import com.example.project.entity.Department;
import com.example.project.entity.Status;
import com.example.project.entity.Type;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DeclarationRepository
        extends CrudRepository<Declaration, Long> {
    Declaration findByNumber(String number);

    @Query("SELECT d FROM Declaration d WHERE d.status = :status")
    Collection<Declaration> findAllByStatus(@Param("status") Status status);

    @Query("SELECT d FROM Declaration d WHERE d.number = :number AND d.department = :department AND d.type = :type")
    Declaration findByRequirements(@Param("department")Department department,
                                   @Param("type") Type type,
                                   @Param("number") String number);
}
