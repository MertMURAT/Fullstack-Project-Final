package com.devaemlak.order_service.producer.dto.enums;

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
