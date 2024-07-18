package com.devaemlak.advertisement_service.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@PrimaryKeyJoinColumn(name = "advertisement_id")
@EntityListeners(AuditingEntityListener.class)
public class SaleAd extends Advertisement{

    @Column(name = "has_garage")
    private Integer garage;

    @Column(name = "has_garden")
    private Integer garden;

    @Column(name = "has_swimming_pool")
    private Integer swimmingPool;

}
