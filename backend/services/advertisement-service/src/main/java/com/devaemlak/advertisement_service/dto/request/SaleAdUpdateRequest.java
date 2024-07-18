package com.devaemlak.advertisement_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaleAdUpdateRequest extends AdvertisementUpdateRequest {

    private Integer garage;
    private Integer garden;
    private Integer swimmingPool;

}
