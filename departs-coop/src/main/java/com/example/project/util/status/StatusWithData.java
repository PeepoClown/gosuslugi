package com.example.project.util.status;

public class StatusWithData<T> extends Status {
    private T data;

    public StatusWithData() {
    }

    public StatusWithData(Integer code, String message, String description, T data) {
        super(code, message, description);
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StatusWithData{" +
                "data=" + data +
                "} " + super.toString();
    }
}
