package com.example.project.repository;

import com.example.project.entity.Declaration;
import com.example.project.entity.Status;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DeclarationRepository extends CrudRepository<Declaration, Long> {
    Declaration findByNumber(String number);

    @Query("SELECT d FROM Declaration d WHERE d.passport = :passport")
    Collection<Declaration> findAllByPassport(@Param("passport") String passport);

    @Query("SELECT d FROM Declaration d WHERE d.status = :status ORDER BY d.createTime")
    Collection<Declaration> findAllByStatus(@Param("status") Status status);
}
