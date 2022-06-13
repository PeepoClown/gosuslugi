package com.example.project.exception;

public class AlreadyExistException
        extends Exception {
    public AlreadyExistException(String message) {
        super(message);
    }
}
