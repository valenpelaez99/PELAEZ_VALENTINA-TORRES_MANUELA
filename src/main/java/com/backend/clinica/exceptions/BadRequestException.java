package com.backend.clinica.exceptions;

public class BadRequestException extends Exception{
    public BadRequestException(String message) {
        super(message);
    }
}
