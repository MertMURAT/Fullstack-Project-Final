package com.devaemlak.advertisement_service.model;

import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.model.enums.AdvertisementType;
import com.devaemlak.advertisement_service.model.enums.HousingType;
import com.devaemlak.advertisement_service.model.enums.PriorityType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "advertisements")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public class Advertisement{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "advertisement_type")
    private AdvertisementType advertisementType;

    @Enumerated(EnumType.STRING)
    @Column(name = "advertisement_status")
    private AdvertisementStatus advertisementStatus;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "number_of_rooms")
    private int numberOfRooms;            // Oda sayısı

    @Column(name = "assignee")
    private String assignee;

    @Column(name = "priority_type")
    @Enumerated(EnumType.STRING)
    private PriorityType priorityType;

    @Column(name = "housing_type")
    private HousingType housingType;          // Emlak tipi (daire, müstakil ev, vb.)

    @Column(name = "advertisement_date")
    private LocalDateTime advertisementDate;

    @Column(name = "user_id")
    private Long userId;

}
