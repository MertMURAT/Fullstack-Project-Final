package com.devaemlak.advertisement_service.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SaleAdResponse extends AdvertisementResponse {

    private double area;
    private boolean hasGarage;
    private boolean hasGarden;
    private boolean hasSwimmingPool;
    private int floorNumber;

}
