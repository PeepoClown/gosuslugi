package com.example.project.entity;

import com.example.project.entity.enums.TypeCode;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "type")
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "label")
    private String label;

    public TypeCode getCode() {
        return code.equals("DOC-PFR") ? TypeCode.DOC_PFR : TypeCode.DOC_FNS;
    }

    public void setCode(TypeCode code) {
        this.code = code.getValue();
    }
}
