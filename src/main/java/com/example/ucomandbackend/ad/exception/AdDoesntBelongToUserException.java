package com.example.ucomandbackend.ad.exception;

import com.example.ucomandbackend.error_handling.common_exception.AbstractOpenApiException;

public class AdDoesntBelongToUserException extends AbstractOpenApiException {

    public static final String CODE = "403";
    public static final String DESC = "Пользователю не принадлежит данное объявление";

    public AdDoesntBelongToUserException(String message) {
        super(CODE, message);
    }
}
