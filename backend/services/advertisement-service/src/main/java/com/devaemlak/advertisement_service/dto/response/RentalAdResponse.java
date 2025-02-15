package com.devaemlak.advertisement_service.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RentalAdResponse extends AdvertisementResponse {

    private BigDecimal monthlyRent;
    private BigDecimal depositAmount;
    private boolean isFurnished;
    private boolean includesUtilities;
    private boolean allowsPets;

}
