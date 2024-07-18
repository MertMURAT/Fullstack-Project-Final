package com.devaemlak.advertisement_service.dto.request;

import com.devaemlak.advertisement_service.model.enums.HousingType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementUpdateRequest {

    private String title;
    private String description;
    private String address;
    private String coordinates;
    private BigDecimal price;
    private int numberOfRooms;
    private double area;
    private String assignee;
    private HousingType housingType;
    private int floorNumber;
    private Long userId;
    private String createdBy;
    private String profileImage;
    private String fullName;

}
