package com.example.ucomandbackend.user.exception;

import com.example.ucomandbackend.error_handling.AbstractOpenApiException;

public class WrongPasswordException extends AbstractOpenApiException {

    public static final String CODE = "401";
    public static final String DESC = "Неверный пароль";

    public WrongPasswordException(String message) {
        super(CODE, message);
    }

    public WrongPasswordException(String message, String userMessage) {
        super(CODE, message, userMessage);
    }

    public WrongPasswordException(String message, Throwable cause) {
        super(CODE, message, cause);
    }

    public WrongPasswordException(String message, String userMessage, Throwable cause) {
        super(CODE, message, userMessage, cause);
    }
}
