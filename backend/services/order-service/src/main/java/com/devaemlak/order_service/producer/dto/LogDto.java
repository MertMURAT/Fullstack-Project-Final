package com.devaemlak.order_service.producer.dto;

import com.devaemlak.order_service.producer.dto.enums.LogType;
import com.devaemlak.order_service.producer.dto.enums.OperationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogDto {
    private String serviceName;
    private OperationType operationType;
    private LogType logType;
    private String message;
    private LocalDateTime timestamp;
    private String exception;
}
