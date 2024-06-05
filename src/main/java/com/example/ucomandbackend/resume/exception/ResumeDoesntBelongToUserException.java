package com.example.ucomandbackend.resume.exception;

import com.example.ucomandbackend.error_handling.common_exception.AbstractOpenApiException;

public class ResumeDoesntBelongToUserException extends AbstractOpenApiException {

    public static final String CODE = "403";
    public static final String DESC = "Пользователю не принадлежит данное резюме";

    public ResumeDoesntBelongToUserException(String message) {
        super(CODE, message);
    }
}
