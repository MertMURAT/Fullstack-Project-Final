package com.devaemlak.advertisement_service.dto.request;

import com.devaemlak.advertisement_service.model.enums.HousingType;
import com.devaemlak.advertisement_service.model.enums.PriorityType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleAdSaveRequest extends AdvertisementSaveRequest{

    private double area;
    private boolean hasGarage;
    private boolean hasGarden;
    private boolean hasSwimmingPool;
    private int floorNumber;

}
