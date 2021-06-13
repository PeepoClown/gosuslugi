package com.example.project.entity;

import com.example.project.entity.enums.TypeCode;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "type")
public class Type implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
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

    public TypeCode getCode() {
        return code.equals("DOC-PFR") ? TypeCode.DOC_PFR : TypeCode.DOC_FNS;
    }

    public void setCode(TypeCode code) {
        this.code = code.getValue();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
