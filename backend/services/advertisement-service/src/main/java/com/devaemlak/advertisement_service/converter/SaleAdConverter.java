package com.devaemlak.advertisement_service.converter;

import com.devaemlak.advertisement_service.dto.request.AdvertisementSaveRequest;
import com.devaemlak.advertisement_service.dto.request.SaleAdSaveRequest;
import com.devaemlak.advertisement_service.dto.response.AdvertisementResponse;
import com.devaemlak.advertisement_service.dto.response.SaleAdResponse;
import com.devaemlak.advertisement_service.model.Advertisement;
import com.devaemlak.advertisement_service.model.SaleAd;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.model.enums.AdvertisementType;
import com.devaemlak.advertisement_service.model.enums.HousingType;
import com.devaemlak.advertisement_service.model.enums.PriorityType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SaleAdConverter {

    public static SaleAd toSaleAd(SaleAdSaveRequest request) {
        return  SaleAd.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .advertisementType(AdvertisementType.SALE)
                .advertisementStatus(AdvertisementStatus.IN_REVIEW)
                .price(request.getPrice())
                .assignee(request.getAssignee())
                .priorityType(request.getPriorityType())
                .housingType(request.getHousingType())
                .advertisementDate(LocalDateTime.now())
                .area(request.getArea())
                .numberOfRooms(request.getNumberOfRooms())
                .hasGarage(request.isHasGarage())
                .hasGarden(request.isHasGarden())
                .hasSwimmingPool(request.isHasSwimmingPool())
                .floorNumber(request.getFloorNumber())
                .userId(request.getUserId())
                .build();
    }

    public static SaleAdResponse toResponse(SaleAd saleAd){
        return SaleAdResponse.builder()
                .id(saleAd.getId())
                .title(saleAd.getTitle())
                .description(saleAd.getDescription())
                .advertisementType(saleAd.getAdvertisementType())
                .advertisementStatus(saleAd.getAdvertisementStatus())
                .price(saleAd.getPrice())
                .assignee(saleAd.getAssignee())
                .numberOfRooms(saleAd.getNumberOfRooms())
                .priorityType(saleAd.getPriorityType())
                .housingType(saleAd.getHousingType())
                .advertisementDate(LocalDateTime.now())
                .userId(saleAd.getUserId())
                .area(saleAd.getArea())
                .hasGarage(saleAd.isHasGarage())
                .hasGarden(saleAd.isHasGarden())
                .hasSwimmingPool(saleAd.isHasSwimmingPool())
                .floorNumber(saleAd.getFloorNumber())
                .build();
    }

    public static List<SaleAdResponse> toResponse(List<SaleAd> saleAds){
        return saleAds.stream()
                .map(SaleAdConverter::toResponse)
                .toList();
    }
}
