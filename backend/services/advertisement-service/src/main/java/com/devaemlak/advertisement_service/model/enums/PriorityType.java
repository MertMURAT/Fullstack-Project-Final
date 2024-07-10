package com.devaemlak.advertisement_service.model.enums;

import lombok.Getter;

@Getter
public enum PriorityType {
    URGENT("Acil"),
    LOW("Düşük Öncelikli"),
    HIGH("Yüksek Öncelikli");

    private String label;

    PriorityType(String label){
        this.label = label;
    }
}
