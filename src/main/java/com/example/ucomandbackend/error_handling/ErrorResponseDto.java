package com.example.ucomandbackend.error_handling;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldNameConstants(innerTypeName = "F")
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