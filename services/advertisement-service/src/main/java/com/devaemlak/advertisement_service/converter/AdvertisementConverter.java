package com.devaemlak.advertisement_service.converter;

import com.devaemlak.advertisement_service.dto.response.AdvertisementResponse;
import com.devaemlak.advertisement_service.dto.request.AdvertisementSaveRequest;
import com.devaemlak.advertisement_service.model.Advertisement;
import com.devaemlak.advertisement_service.model.SaleAd;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.model.enums.HousingType;
import com.devaemlak.advertisement_service.model.enums.PriorityType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdvertisementConverter {

    public static Advertisement toAdvertisement(AdvertisementSaveRequest request) {
        return Advertisement.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .advertisementStatus(AdvertisementStatus.IN_REVIEW)
                .price(request.getPrice())
                .assignee(request.getAssignee())
                .priorityType(request.getPriorityType())
                .housingType(request.getHousingType())
                .advertisementDate(LocalDateTime.now())
                .userId(request.getUserId())
                .build();
    }


    public static AdvertisementResponse toResponse(Advertisement advertisement){
        return AdvertisementResponse.builder()
                .id(advertisement.getId())
                .title(advertisement.getTitle())
                .description(advertisement.getDescription())
                .advertisementStatus(advertisement.getAdvertisementStatus())
                .price(advertisement.getPrice())
                .assignee(advertisement.getAssignee())
                .priorityType(advertisement.getPriorityType())
                .housingType(advertisement.getHousingType())
                .advertisementDate(advertisement.getAdvertisementDate())
                .userId(advertisement.getUserId())
                .build();
    }

    public static List<AdvertisementResponse> toResponse(List<Advertisement> advertisements){
        return advertisements.stream()
                .map(AdvertisementConverter::toResponse)
                .toList();
    }
}
