package com.devaemlak.log_service.exception;

import com.devaemlak.log_service.dto.response.ExceptionResponse;
import com.devaemlak.log_service.dto.response.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LogException.class)
    public GenericResponse<ExceptionResponse> handleException(LogException exception) {
        log.error(exception.getLocalizedMessage());
        return GenericResponse.failed(exception.getMessage());
    }
}
