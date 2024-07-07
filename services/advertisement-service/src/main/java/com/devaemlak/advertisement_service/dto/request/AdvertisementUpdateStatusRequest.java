package com.devaemlak.advertisement_service.dto.request;

import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementUpdateStatusRequest {
    private Long id;
    private AdvertisementStatus status;
}
