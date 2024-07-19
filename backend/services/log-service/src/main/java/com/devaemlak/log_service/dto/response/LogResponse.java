package com.devaemlak.log_service.dto.response;

import com.devaemlak.log_service.model.enums.OperationType;
import com.devaemlak.log_service.model.enums.LogType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogResponse {

    private String id;
    private String serviceName;
    private OperationType operationType;
    private LogType logType;
    private String message;
    private LocalDateTime timestamp;
    private String exception;

}
