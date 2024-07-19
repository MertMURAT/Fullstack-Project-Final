package com.devaemlak.log_service.converter;

import com.devaemlak.log_service.consumer.response.LogDto;
import com.devaemlak.log_service.dto.request.LogSaveRequest;
import com.devaemlak.log_service.dto.request.LogUpdateRequest;
import com.devaemlak.log_service.dto.response.LogResponse;
import com.devaemlak.log_service.model.Log;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class LogConverter {

    public static Log toLog(LogDto logDto){
        return Log.builder()
                .serviceName(logDto.getServiceName())
                .operationType(logDto.getOperationType())
                .logType(logDto.getLogType())
                .message(logDto.getMessage())
                .timestamp(LocalDateTime.now())
                .exception(logDto.getException())
                .build();
    }

    public static Log toLog(LogSaveRequest request){
        return Log.builder()
                .serviceName(request.getServiceName())
                .operationType(request.getOperationType())
                .logType(request.getLogType())
                .message(request.getMessage())
                .timestamp(LocalDateTime.now())
                .exception(request.getException())
                .build();
    }

    public static Log toLog(Log log, LogUpdateRequest request){

        log.setServiceName(request.getServiceName());
        log.setOperationType(request.getOperationType());
        log.setLogType(request.getLogType());
        log.setMessage(request.getMessage());
        log.setTimestamp(LocalDateTime.now());
        log.setException(request.getException());

        return log;
    }

    public static LogResponse toResponse(Log log){
        return LogResponse.builder()
                .id(log.getId())
                .serviceName(log.getServiceName())
                .operationType(log.getOperationType())
                .logType(log.getLogType())
                .message(log.getMessage())
                .timestamp(LocalDateTime.now())
                .exception(log.getException())
                .build();
    }

    public static List<LogResponse> toResponse(List<Log> logs){
        return logs.stream()
                .map(LogConverter::toResponse)
                .toList();
    }
}
