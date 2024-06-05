package com.example.ucomandbackend.error_handling.common_exception;

public class CorruptedTokenException extends AbstractOpenApiException {
    public static final String CODE = "401";
    public static final String DESC = "Не валидный токен";

    public CorruptedTokenException(String message) {
        super(CODE, message);
    }
}
