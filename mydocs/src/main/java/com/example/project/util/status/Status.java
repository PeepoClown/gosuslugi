package com.example.project.util.status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Status
        implements Serializable {
    private Integer code;
    private String message;
    private String description;
}
