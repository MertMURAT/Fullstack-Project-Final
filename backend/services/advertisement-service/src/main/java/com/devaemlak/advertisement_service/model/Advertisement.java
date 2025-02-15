package com.devaemlak.advertisement_service.model;
import com.devaemlak.advertisement_service.config.PointSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.locationtech.jts.geom.Point;

import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.model.enums.AdvertisementType;
import com.devaemlak.advertisement_service.model.enums.HousingType;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "advertisements")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@EntityListeners(AuditingEntityListener.class)
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;


    @JsonSerialize(using = PointSerializer.class)
    @Column(name = "coordinates", columnDefinition = "geography(Point, 4326)")
    private Point coordinates;

    @Enumerated(EnumType.STRING)
    @Column(name = "advertisement_type")
    private AdvertisementType advertisementType;

    @Enumerated(EnumType.STRING)
    @Column(name = "advertisement_status")
    private AdvertisementStatus advertisementStatus;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "number_of_rooms")
    private int numberOfRooms;

    @Column(name = "area")
    private double area;

    @Column(name = "assignee")
    private String assignee;

    @Column(name = "housing_type")
    private HousingType housingType;

    @Column(name = "floor_number")
    private int floorNumber;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "createdBy")
    private String createdBy;

    @Column(name = "profileImage")
    private String profileImage;

    @Column(name = "fullName")
    private String fullName;

    @JsonManagedReference
    @OneToMany(mappedBy = "advertisement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;
}
