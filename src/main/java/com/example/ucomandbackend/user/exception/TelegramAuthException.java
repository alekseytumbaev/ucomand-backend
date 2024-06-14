package com.example.ucomandbackend.user.exception;

import com.example.ucomandbackend.error_handling.common_exception.AbstractOpenApiException;

public class TelegramAuthException extends AbstractOpenApiException {
    public static final String CODE = "401";
    public static final String DESC = "Не найдено";

    public TelegramAuthException(String message) {
        super(CODE, DESC, message);
    }
}
