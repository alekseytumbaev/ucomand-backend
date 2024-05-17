package com.example.ucomandbackend.error_handling;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Базовое исключение для всех исключений, которые отображаются в разделе response в open api.
 * <br>
 * Наследники ОБЯЗАНЫ иметь константы:
 * <ul>
 * <li>{@code public static final String CODE} - http статус в виде строки: "404", "500", и т.д.</li>
 * <li>{@code public static final String DESC} - описание ответа</li>
 * </ul>
 * <br>
 * Такой костыль нужен, чтобы не хордкодить код и описание в open api, а ссылаться на исключения.
 */
@Getter
@Setter
public abstract class AbstractOpenApiException extends RuntimeException {

    private HttpStatus httpStatus;

    /**
     * Описание ошибки, предназначенное для пользователя
     */
    private String userMessage;

    /**
     * @param httpStatus http статус ввиде строки: "404", "500", и т.д.
     */
    protected AbstractOpenApiException(String httpStatus, String message) {
        super(message);
        this.httpStatus = HttpStatus.valueOf(Integer.parseInt(httpStatus));
        this.userMessage = message;
    }

    /**
     * @param httpStatus http статус ввиде строки: "404", "500", и т.д.
     */
    protected AbstractOpenApiException(String httpStatus, String message, String userMessage) {
        super(message);
        this.httpStatus = HttpStatus.valueOf(Integer.parseInt(httpStatus));
        this.userMessage = userMessage;
    }

    /**
     * @param httpStatus http статус ввиде строки: "404", "500", и т.д.
     */
    protected AbstractOpenApiException(String httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = HttpStatus.valueOf(Integer.parseInt(httpStatus));
        this.userMessage = message;
    }

    /**
     * @param httpStatus http статус ввиде строки: "404", "500", и т.д.
     */
    protected AbstractOpenApiException(String httpStatus, String message, String userMessage, Throwable cause) {
        super(message, cause);
        this.httpStatus = HttpStatus.valueOf(Integer.parseInt(httpStatus));
        this.userMessage = userMessage;
    }
}
