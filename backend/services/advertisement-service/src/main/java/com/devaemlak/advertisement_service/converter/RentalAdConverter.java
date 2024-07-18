package com.devaemlak.advertisement_service.converter;

import com.devaemlak.advertisement_service.dto.request.AdvertisementSaveRequest;
import com.devaemlak.advertisement_service.dto.request.RentalAdUpdateRequesst;
import com.devaemlak.advertisement_service.dto.response.RentalAdResponse;
import com.devaemlak.advertisement_service.model.Advertisement;
import com.devaemlak.advertisement_service.model.RentalAd;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.model.enums.AdvertisementType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RentalAdConverter {

    public static RentalAd toRentalAd(RentalAdUpdateRequesst request) {
        return  RentalAd.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .address(request.getAddress())
                .coordinates(request.getCoordinates())
                .advertisementType(AdvertisementType.RENTAL)
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

                .monthlyRent(request.getMonthlyRent())
                .depositAmount(request.getDepositAmount())
                .isFurnished(request.isFurnished())
                .includesUtilities(request.isIncludesUtilities())
                .allowsPets(request.isAllowsPets())
                .build();
    }

    public static RentalAdResponse toResponse(RentalAd rentalAd){
        return RentalAdResponse.builder()
                .id(rentalAd.getId())
                .title(rentalAd.getTitle())
                .description(rentalAd.getDescription())
                .address(rentalAd.getAddress())
                .coordinates(rentalAd.getCoordinates())
                .advertisementType(rentalAd.getAdvertisementType())
                .advertisementStatus(rentalAd.getAdvertisementStatus())
                .price(rentalAd.getPrice())
                .numberOfRooms(rentalAd.getNumberOfRooms())
                .area(rentalAd.getArea())
                .assignee(rentalAd.getAssignee())
                .housingType(rentalAd.getHousingType())
                .floorNumber(rentalAd.getFloorNumber())
                .created_at(rentalAd.getCreated_at())
                .userId(rentalAd.getUserId())
                .createdBy(rentalAd.getCreatedBy())
                .profileImage(rentalAd.getProfileImage())
                .fullName(rentalAd.getFullName())

                .monthlyRent(rentalAd.getMonthlyRent())
                .depositAmount(rentalAd.getDepositAmount())
                .isFurnished(rentalAd.isFurnished())
                .includesUtilities(rentalAd.isIncludesUtilities())
                .allowsPets(rentalAd.isAllowsPets())
                .build();
    }

    public static RentalAd toRentalInit(AdvertisementSaveRequest request){
        return RentalAd.builder()
                .address(request.getAddress())
                .advertisementType(AdvertisementType.RENTAL)
                .advertisementStatus(AdvertisementStatus.IN_REVIEW)
                .coordinates(request.getCoordinates())
                .createdBy(request.getCreatedBy())
                .created_at(LocalDateTime.now())
                .build();
    }

    public static Advertisement updateAdFromRentalAd(Advertisement advertisement, RentalAd rentalAd) {
        advertisement.setTitle(rentalAd.getTitle());
        advertisement.setDescription(rentalAd.getDescription());
        advertisement.setPrice(rentalAd.getPrice());
        advertisement.setNumberOfRooms(rentalAd.getNumberOfRooms());
        advertisement.setArea(rentalAd.getArea());
        advertisement.setAssignee(rentalAd.getAssignee());
        advertisement.setHousingType(rentalAd.getHousingType());
        advertisement.setFloorNumber(rentalAd.getFloorNumber());
        advertisement.setCreatedBy(rentalAd.getCreatedBy());
        advertisement.setProfileImage(rentalAd.getProfileImage());
        advertisement.setFullName(rentalAd.getFullName());
        advertisement.setUpdated_at(rentalAd.getUpdated_at());
        advertisement.setUserId(rentalAd.getUserId());

        if (advertisement instanceof RentalAd rentalAdInstance) {
            rentalAdInstance.setMonthlyRent(rentalAd.getMonthlyRent());
            rentalAdInstance.setDepositAmount(rentalAd.getDepositAmount());
            rentalAdInstance.setFurnished(rentalAd.isFurnished());
            rentalAdInstance.setIncludesUtilities(rentalAd.isIncludesUtilities());
            rentalAdInstance.setAllowsPets(rentalAd.isAllowsPets());
        }
        return advertisement;
    }

    public static List<RentalAdResponse> toResponse(List<RentalAd> ads){
        return ads.stream()
                .map(RentalAdConverter::toResponse)
                .toList();
    }
}
