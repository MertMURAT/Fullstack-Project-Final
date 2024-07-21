package com.devaemlak.advertisement_service.dto.request;

import com.devaemlak.advertisement_service.model.enums.HousingType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementSearchRequest extends BaseSaveRequest{

    private String address;
    private double area;
    private int numberOfRooms;
    private int floorNumber;
    private HousingType housingType;

}
