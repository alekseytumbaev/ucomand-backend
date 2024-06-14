package com.example.ucomandbackend.error_handling.common_exception;

public class InternalErrorException extends AbstractOpenApiException {
    public static final String CODE = "500";
    public static final String DESC = "Внутренняя ошибка";

    public InternalErrorException(String message, Throwable cause) {
        super(CODE, message, cause);
    }
}
