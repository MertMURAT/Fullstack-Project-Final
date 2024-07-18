package com.devaemlak.advertisement_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentalAdUpdateRequesst extends AdvertisementUpdateRequest {

    private BigDecimal monthlyRent;
    private BigDecimal depositAmount;
    private boolean isFurnished;
    private boolean includesUtilities;
    private boolean allowsPets;

}
