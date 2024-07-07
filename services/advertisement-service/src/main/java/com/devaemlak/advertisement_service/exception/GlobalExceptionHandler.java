package com.devaemlak.advertisement_service.exception;


import com.devaemlak.advertisement_service.dto.response.ExceptionResponse;
import com.devaemlak.advertisement_service.dto.response.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AdvertisementException.class)
    public GenericResponse<ExceptionResponse> handleException(AdvertisementException exception) {
        log.error(exception.getLocalizedMessage());
        return GenericResponse.failed(exception.getMessage());
    }
}
