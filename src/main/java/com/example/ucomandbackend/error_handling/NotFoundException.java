package com.example.ucomandbackend.error_handling;

public class NotFoundException extends AbstractOpenApiException {
    public static final String CODE = "404";
    public static final String DESC = "Не найдено";

    public NotFoundException(String message) {
        super(CODE, message);
    }

    public NotFoundException(String message, String userMessage) {
        super(CODE, message, userMessage);
    }

    public NotFoundException(String message, Throwable cause) {
        super(CODE, message, cause);
    }

    public NotFoundException(String message, String userMessage, Throwable cause) {
        super(CODE, message, userMessage, cause);
    }
}
