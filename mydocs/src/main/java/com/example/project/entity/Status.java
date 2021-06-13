package com.example.project.entity;

import com.example.project.entity.enums.StatusCode;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "status")
public class Status implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "label")
    private String label;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusCode getCode() {
        return code.equals("NEW") ? StatusCode.NEW :
                code.equals("PROCESSING") ? StatusCode.PROCESSING : StatusCode.COMPLETED;
    }

    public void setCode(StatusCode code) {
        this.code = code.getValue();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
