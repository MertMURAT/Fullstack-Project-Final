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
public class RentalAdSaveRequest extends AdvertisementSaveRequest {

    private BigDecimal monthlyRent;
    private double area;
    private BigDecimal depositAmount;
    private boolean isFurnished;
    private boolean includesUtilities;
    private int leaseTerm;
    private boolean allowsPets;

}
