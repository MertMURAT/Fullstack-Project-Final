package com.devaemlak.advertisement_service.model;

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
public class RentalAd extends Advertisement{

    @Column(name = "monthly_rent")
    private BigDecimal monthlyRent;       // Aylık kira

    @Column(name = "deposit_amount")
    private BigDecimal depositAmount;     // Depozito miktarı

    @Column(name = "is_furnished")
    private boolean isFurnished;          // Eşyalı mı

    @Column(name = "includes_utilities")
    private boolean includesUtilities;    // Aidat ve diğer masraflar dahil mi

    @Column(name = "allows_pets")
    private boolean allowsPets;           // Evcil hayvanlara izin veriliyor mu

}
