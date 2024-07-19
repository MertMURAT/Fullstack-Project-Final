package com.devaemlak.log_service.model.enums;

import com.devaemlak.log_service.model.Log;
import lombok.Getter;

@Getter
public enum LogType {
    ERROR("ERROR"),
    WARN("WARN"),
    INFO("INFO");

    private final String message;

    LogType(String message) {
        this.message = message;
    }
}
