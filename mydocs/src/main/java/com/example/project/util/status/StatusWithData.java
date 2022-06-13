package com.example.project.util.status;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StatusWithData<T>
        extends Status {
    private T data;

    public StatusWithData(Integer code, String message, String description, T data) {
        super(code, message, description);
        this.data = data;
    }
}
