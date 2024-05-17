package com.example.ucomandbackend.error_handling;

public class CorruptedTokenException extends AbstractOpenApiException {
    public static final String CODE = "401";
    public static final String DESC = "Не валидный токен";

    public CorruptedTokenException(String message) {
        super(CODE, message);
    }

    public CorruptedTokenException(String httpStatus, String message, String userMessage) {
        super(httpStatus, message, userMessage);
    }

    public CorruptedTokenException(String httpStatus, String message, Throwable cause) {
        super(httpStatus, message, cause);
    }

    public CorruptedTokenException(String httpStatus, String message, String userMessage, Throwable cause) {
        super(httpStatus, message, userMessage, cause);
    }
}
