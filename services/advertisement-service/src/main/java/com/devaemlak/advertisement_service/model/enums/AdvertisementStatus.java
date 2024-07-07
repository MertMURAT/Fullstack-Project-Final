package com.devaemlak.advertisement_service.model.enums;

import lombok.Getter;

@Getter
public enum AdvertisementStatus {
    IN_REVIEW("İncelemede"),
    ACTIVE("Aktif İlan"),
    PASSIVE("Pasif İlan");

    private String label;

    AdvertisementStatus(String label){
        this.label = label;
    }
}
