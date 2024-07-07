package com.devaemlak.advertisement_service.model;

import com.devaemlak.advertisement_service.model.enums.HousingType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@PrimaryKeyJoinColumn(name = "advertisement_id")
public class SaleAd extends Advertisement{

    @Column(name = "area")
    private double area;                  // Alan (metrekare)

    @Column(name = "has_garage")
    private boolean hasGarage;            // Garaj var mı

    @Column(name = "has_garden")
    private boolean hasGarden;            // Bahçe var mı

    @Column(name = "has_swimming_pool")
    private boolean hasSwimmingPool;      // Yüzme havuzu var mı

    @Column(name = "floor_number")
    private int floorNumber;              // Kat numarası

}
