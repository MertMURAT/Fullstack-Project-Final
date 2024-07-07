package com.devaemlak.advertisement_service.model.enums;

import lombok.Getter;

@Getter
public enum HousingType {
    APARTMENT("Daire"),
    VILLA("Villa"),
    PRIVATE("Müstakil"),
    BUILDING("Bina"),
    SUMMERY("Yazlık"),
    PREFABRICATED("Prefabrik");

    private String label;

    HousingType(String label){
        this.label = label;
    }
}
