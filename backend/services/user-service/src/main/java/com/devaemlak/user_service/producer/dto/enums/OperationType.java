package com.devaemlak.user_service.producer.dto.enums;

import lombok.Getter;

@Getter
public enum OperationType {
    INSERT("insert"),
    UPDATE("update"),
    GET("get"),
    REPLACE("replace"),
    DELETE("delete"),
    INVALIDATE("invalidate"),
    DROP("drop"),
    DROP_DATABASE("dropDatabase"),
    RENAME("rename"),
    OTHER("other");

    private final String message;

    private OperationType(String message) {
        this.message = message;
    }
}