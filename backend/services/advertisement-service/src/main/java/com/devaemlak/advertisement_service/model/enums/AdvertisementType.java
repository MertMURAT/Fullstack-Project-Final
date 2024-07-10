package com.devaemlak.advertisement_service.model.enums;

import lombok.Getter;

@Getter
public enum AdvertisementType {

    RENTAL("Kiralık"),
    SALE("Satılık");

    private String label;

    AdvertisementType(String label){
        this.label = label;
    }
}
