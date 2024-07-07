package com.devaemlak.advertisement_service.converter;

import com.devaemlak.advertisement_service.dto.request.RentalAdSaveRequest;
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

    public static RentalAd toRentalAd(RentalAdSaveRequest request) {
        return  RentalAd.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .advertisementType(AdvertisementType.RENTAL)
                .advertisementStatus(AdvertisementStatus.IN_REVIEW)
                .price(request.getPrice())
                .numberOfRooms(request.getNumberOfRooms())
                .assignee(request.getAssignee())
                .priorityType(request.getPriorityType())
                .housingType(request.getHousingType())
                .advertisementDate(LocalDateTime.now())
                .userId(request.getUserId())
                .monthlyRent(request.getMonthlyRent())
                .area(request.getArea())
                .depositAmount(request.getDepositAmount())
                .isFurnished(request.isFurnished())
                .includesUtilities(request.isIncludesUtilities())
                .leaseTerm(request.getLeaseTerm())
                .allowsPets(request.isAllowsPets())
                .build();
    }

    public static RentalAdResponse toResponse(RentalAd rentalAd){
        return RentalAdResponse.builder()
                .id(rentalAd.getId())
                .title(rentalAd.getTitle())
                .description(rentalAd.getDescription())
                .advertisementType(rentalAd.getAdvertisementType())
                .advertisementStatus(rentalAd.getAdvertisementStatus())
                .price(rentalAd.getPrice())
                .numberOfRooms(rentalAd.getNumberOfRooms())
                .assignee(rentalAd.getAssignee())
                .priorityType(rentalAd.getPriorityType())
                .housingType(rentalAd.getHousingType())
                .advertisementDate(LocalDateTime.now())
                .userId(rentalAd.getUserId())
                .monthlyRent(rentalAd.getMonthlyRent())
                .area(rentalAd.getArea())
                .depositAmount(rentalAd.getDepositAmount())
                .isFurnished(rentalAd.isFurnished())
                .includesUtilities(rentalAd.isIncludesUtilities())
                .leaseTerm(rentalAd.getLeaseTerm())
                .allowsPets(rentalAd.isAllowsPets())
                .build();
    }

    public static List<RentalAdResponse> toResponse(List<RentalAd> ads){
        return ads.stream()
                .map(RentalAdConverter::toResponse)
                .toList();
    }
}
