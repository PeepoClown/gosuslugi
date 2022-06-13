package com.example.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Random;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EqualsAndHashCode(callSuper = true)
@SequenceGenerator(allocationSize = 1, name = "seq_gen", sequenceName = "declaration_id_seq")
@Table(name = "declaration")
public class Declaration
        extends AbstractEntity {
    @Column(name = "number", unique = true, nullable = false)
    private String number;

    @Column(name = "client_initials", nullable = false)
    private String clientInitials;

    @Column(name = "passport", nullable = false)
    private String passport;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @ManyToOne(optional = false)
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private Type type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @ManyToOne(optional = false)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;

    private static final int DECL_NUMBER_SYMBOLS = 15;

    public static String generateNumber() {
        StringBuilder sb = new StringBuilder("DECL_");
        Random random = new Random();
        for (int i = 0; i < DECL_NUMBER_SYMBOLS; i++) {
            int numOrChar = (int) (random.nextFloat() * 3);
            int randInt = 0;
            switch (numOrChar) {
                case 0:
                    randInt = '0' + (int) (random.nextFloat() * ('9' - '0' + 1));
                    break ;
                case 1:
                    randInt = 'A' + (int) (random.nextFloat() * ('Z' - 'A' + 1));
                    break ;
                case 2:
                    randInt = 'a' + (int) (random.nextFloat() * ('z' - 'a' + 1));
            }
            sb.append((char) randInt);
        }
        return sb.toString();
    }
}
