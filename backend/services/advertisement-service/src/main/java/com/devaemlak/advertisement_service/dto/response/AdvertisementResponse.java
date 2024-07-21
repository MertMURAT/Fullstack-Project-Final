package com.devaemlak.advertisement_service.dto.response;

import com.devaemlak.advertisement_service.dto.request.Coordinates;
import com.devaemlak.advertisement_service.model.Image;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.model.enums.AdvertisementType;
import com.devaemlak.advertisement_service.model.enums.HousingType;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementResponse {

    private Long id;
    private String title;
    private String description;
    private String address;
    private Coordinates coordinates;
    private AdvertisementType advertisementType;
    private AdvertisementStatus advertisementStatus;
    private BigDecimal price;
    private int numberOfRooms;
    private double area;
    private String assignee;
    private HousingType housingType;
    private int floorNumber;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    private Long userId;
    private String createdBy;
    private String profileImage;
    private String fullName;
    private List<Image> images;

}
