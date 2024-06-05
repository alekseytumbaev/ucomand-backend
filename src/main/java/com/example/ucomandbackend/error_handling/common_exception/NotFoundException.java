package com.example.ucomandbackend.error_handling.common_exception;

public class NotFoundException extends AbstractOpenApiException {
    public static final String CODE = "404";
    public static final String DESC = "Не найдено";

    public NotFoundException(String message) {
        super(CODE, message);
    }
}
