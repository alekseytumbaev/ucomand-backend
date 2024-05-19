package com.example.ucomandbackend.error_handling;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AbstractOpenApiException.class)
    public ResponseEntity<ErrorResponseDto> onAbstractOpenApiException(AbstractOpenApiException e) {
        var error = new ErrorResponseDto(
                e.getHttpStatus().value(),
                e.getUserMessage(),
                e.getMessage(),
                e.getClass().getName(),
                null);

        addStackTrace(error, e);
        return ResponseEntity.status(e.getHttpStatus()).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(CONFLICT)
    public ErrorResponseDto onDataIntegrityViolationException(DataIntegrityViolationException e) {
        var error = new ErrorResponseDto(
                CONFLICT.value(),
                "Некорректные данные",
                "Ошибка при сохранении данных в базу: " + e.getMessage(),
                e.getClass().getName(),
                null);

        addStackTrace(error, e);
        return error;
    }

    /**
     * Ошибка валидации параметров, переменных, заголовков запроса
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponseDto onConstraintViolationException(ConstraintViolationException e) {
        var error = new ErrorResponseDto(
                BAD_REQUEST.value(),
                "Некорректные данные",
                "Ошибка валидации: " + e.getMessage(),
                e.getClass().getName(),
                null);

        addStackTrace(error, e);
        return error;
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponseDto onThrowable(Throwable e) {
        var error = new ErrorResponseDto(
                INTERNAL_SERVER_ERROR.value(),
                "Неизвестная ошибка",
                "Ошибка сервера: " + e.getMessage(),
                e.getClass().getName(),
                null);

        addStackTrace(error, e);
        return error;
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             @NonNull HttpHeaders headers,
                                                             HttpStatusCode statusCode,
                                                             @NonNull WebRequest request) {
        var error = new ErrorResponseDto(
                statusCode.value(),
                ex.getMessage(),
                ex.getMessage(),
                ex.getClass().getName(),
                null);

        if (request instanceof ServletWebRequest servletWebRequest) {
            HttpServletResponse response = servletWebRequest.getResponse();
            if (response != null && response.isCommitted()) {
                if (logger.isWarnEnabled()) {
                    logger.warn("Response already committed. Ignoring: " + ex);
                }
                return null;
            }
        }

        if (body == null && ex instanceof ErrorResponse errorResponse) {
            var problemDetail = errorResponse.updateAndGetBody(null, LocaleContextHolder.getLocale());
            error.setUserMessage(problemDetail.getDetail());
        }

        if (body instanceof ProblemDetail problemDetail) {
            error.setUserMessage(problemDetail.getDetail());
        }

        addStackTrace(error, ex);
        return createResponseEntity(error, headers, statusCode, request);
    }

    private void addStackTrace(ErrorResponseDto errorResponseDto, Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        errorResponseDto.setStackTrace(sw.toString());
    }
}
