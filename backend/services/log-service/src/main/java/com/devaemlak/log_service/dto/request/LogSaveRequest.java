package com.devaemlak.log_service.dto.request;

import com.devaemlak.log_service.model.enums.OperationType;
import com.devaemlak.log_service.model.enums.LogType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LogSaveRequest {
    private String serviceName;
    private OperationType operationType;
    private LogType logType;
    private String message;
    private LocalDateTime timestamp;
    private String exception;
}
