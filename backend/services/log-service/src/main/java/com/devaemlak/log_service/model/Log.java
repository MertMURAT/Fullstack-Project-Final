package com.devaemlak.log_service.model;

import com.devaemlak.log_service.model.enums.OperationType;
import com.devaemlak.log_service.model.enums.LogType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document
@AllArgsConstructor
@NoArgsConstructor
public class Log {

    @Id
    private String id;

    @Field(name = "service_name")
    private String serviceName;

    @Enumerated(EnumType.STRING)
    @Field(name = "operation_type")
    private OperationType operationType;

    @Enumerated(EnumType.STRING)
    @Field(name = "log_type")
    private LogType logType;

    @Field(name = "message")
    private String message;

    @Field(name = "timestamp")
    private LocalDateTime timestamp;

    @Field(name = "exception")
    private String exception;
}
