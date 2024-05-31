package com.example.ucomandbackend.error_handling;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class ErrorResponseDto {

    private int httpStatus;

    @Schema(description = "Сообщение для пользователя")
    private String userMessage;

    @Schema(description = "Подробное описание ошибки для разработчика")
    private String details;

    private String exception;

    private final Instant timestamp = Instant.now();

    private String stackTrace;
}