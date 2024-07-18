package com.devaemlak.advertisement_service.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SaleAdResponse extends AdvertisementResponse {

    private Integer garage;
    private Integer garden;
    private Integer swimmingPool;

}
