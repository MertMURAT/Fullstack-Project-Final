package com.devaemlak.order_service.converter;

import com.devaemlak.order_service.dto.response.AdPackageResponse;
import com.devaemlak.order_service.model.AdPackage;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdPackageConverter {

    public static AdPackageResponse toResponse(AdPackage adPackage){
        return AdPackageResponse.builder()
                .quantity(adPackage.getQuantity())
                .createdDate(adPackage.getCreatedDate())
                .expiryDate(adPackage.getExpiryDate())
                .build();
    }

    public static List<AdPackageResponse> toResponse(List<AdPackage> adPackages){
        return adPackages.stream()
                .map(AdPackageConverter::toResponse)
                .toList();
    }
}
