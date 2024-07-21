package com.devaemlak.advertisement_service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementSaveRequest {

    private String address;
    private Coordinates coordinates;
    private String createdBy;

}
