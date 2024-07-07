package com.devaemlak.advertisement_service.dto.request;

import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.model.enums.HousingType;
import com.devaemlak.advertisement_service.model.enums.PriorityType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementSaveRequest {

    private String title;
    private String description;
    private BigDecimal price;
    private int numberOfRooms;
    private String assignee;
    private PriorityType priorityType;
    private HousingType housingType;
    private LocalDateTime advertisementDate;
    private Long userId;
}
