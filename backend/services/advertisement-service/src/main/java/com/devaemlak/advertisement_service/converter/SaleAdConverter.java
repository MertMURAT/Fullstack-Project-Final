package com.devaemlak.advertisement_service.converter;

import com.devaemlak.advertisement_service.dto.request.AdvertisementSaveRequest;
import com.devaemlak.advertisement_service.dto.request.SaleAdUpdateRequest;
import com.devaemlak.advertisement_service.dto.response.SaleAdResponse;
import com.devaemlak.advertisement_service.model.Advertisement;
import com.devaemlak.advertisement_service.model.SaleAd;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.model.enums.AdvertisementType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SaleAdConverter {

    public static SaleAd toSaleAd(SaleAdUpdateRequest request) {
        return  SaleAd.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .address(request.getAddress())
                .coordinates(request.getCoordinates())
                .advertisementType(AdvertisementType.SALE)
                .advertisementStatus(AdvertisementStatus.IN_REVIEW)
                .price(request.getPrice())
                .numberOfRooms(request.getNumberOfRooms())
                .area(request.getArea())
                .assignee(request.getAssignee())
                .housingType(request.getHousingType())
                .floorNumber(request.getFloorNumber())
                .created_at(LocalDateTime.now())
                .userId(request.getUserId())
                .createdBy(request.getCreatedBy())
                .profileImage(request.getProfileImage())
                .fullName(request.getFullName())

                .garage(request.getGarage())
                .garden(request.getGarden())
                .swimmingPool(request.getSwimmingPool())
                .build();
    }

    public static SaleAdResponse toResponse(SaleAd saleAd){
        return SaleAdResponse.builder()
                .id(saleAd.getId())
                .title(saleAd.getTitle())
                .description(saleAd.getDescription())
                .address(saleAd.getAddress())
                .coordinates(saleAd.getCoordinates())
                .advertisementType(saleAd.getAdvertisementType())
                .advertisementStatus(saleAd.getAdvertisementStatus())
                .price(saleAd.getPrice())
                .numberOfRooms(saleAd.getNumberOfRooms())
                .area(saleAd.getArea())
                .assignee(saleAd.getAssignee())
                .housingType(saleAd.getHousingType())
                .floorNumber(saleAd.getFloorNumber())
                .created_at(saleAd.getCreated_at())
                .userId(saleAd.getUserId())
                .createdBy(saleAd.getCreatedBy())
                .profileImage(saleAd.getProfileImage())
                .fullName(saleAd.getFullName())
                .images(saleAd.getImages())

                .garage(saleAd.getGarage())
                .garden(saleAd.getGarden())
                .swimmingPool(saleAd.getSwimmingPool())
                .build();
    }

    public static SaleAd toSaleInit(AdvertisementSaveRequest request){
        return SaleAd.builder()
                .address(request.getAddress())
                .advertisementType(AdvertisementType.SALE)
                .advertisementStatus(AdvertisementStatus.IN_REVIEW)
                .coordinates(request.getCoordinates())
                .createdBy(request.getCreatedBy())
                .created_at(LocalDateTime.now())
                .build();
    }

    public static Advertisement updateAdFromSaleAd(Advertisement advertisement, SaleAd saleAd) {
        advertisement.setTitle(saleAd.getTitle());
        advertisement.setDescription(saleAd.getDescription());
        advertisement.setPrice(saleAd.getPrice());
        advertisement.setNumberOfRooms(saleAd.getNumberOfRooms());
        advertisement.setArea(saleAd.getArea());
        advertisement.setAssignee(saleAd.getAssignee());
        advertisement.setHousingType(saleAd.getHousingType());
        advertisement.setFloorNumber(saleAd.getFloorNumber());
        advertisement.setCreatedBy(saleAd.getCreatedBy());
        advertisement.setProfileImage(saleAd.getProfileImage());
        advertisement.setFullName(saleAd.getFullName());
        advertisement.setUpdated_at(LocalDateTime.now());
        advertisement.setUserId(saleAd.getUserId());

        if (advertisement instanceof SaleAd saleAdInstance) {
            saleAdInstance.setGarage(saleAd.getGarage());
            saleAdInstance.setGarden(saleAd.getGarden());
            saleAdInstance.setSwimmingPool(saleAd.getSwimmingPool());
        }
        return advertisement;
    }


    public static List<SaleAdResponse> toResponse(List<SaleAd> saleAds){
        return saleAds.stream()
                .map(SaleAdConverter::toResponse)
                .toList();
    }
}
