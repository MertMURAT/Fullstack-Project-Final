package com.devaemlak.advertisement_service.converter;

import com.devaemlak.advertisement_service.dto.request.AdvertisementSaveRequest;
import com.devaemlak.advertisement_service.dto.response.SaleAdResponse;
import com.devaemlak.advertisement_service.model.Advertisement;
import com.devaemlak.advertisement_service.model.SaleAd;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdvertisementConverter {

    public static Advertisement toAdvertisement(AdvertisementSaveRequest request){
        return Advertisement.builder()
                .address(request.getAddress())
                .advertisementStatus(AdvertisementStatus.IN_REVIEW)
                .coordinates(request.getCoordinates())
                .createdBy(request.getCreatedBy())
                .created_at(LocalDateTime.now())
                .build();
    }
}
