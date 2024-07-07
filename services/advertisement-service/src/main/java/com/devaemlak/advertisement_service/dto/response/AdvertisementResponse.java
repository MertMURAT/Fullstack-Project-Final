package com.devaemlak.advertisement_service.dto.response;

import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.model.enums.AdvertisementType;
import com.devaemlak.advertisement_service.model.enums.HousingType;
import com.devaemlak.advertisement_service.model.enums.PriorityType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementResponse {

    private Long id;
    private String title;
    private String description;
    private AdvertisementType advertisementType;
    private AdvertisementStatus advertisementStatus;
    private BigDecimal price;
    private int numberOfRooms;
    private String assignee;
    private PriorityType priorityType;
    private HousingType housingType;
    private LocalDateTime advertisementDate;
    private Long userId;
}
