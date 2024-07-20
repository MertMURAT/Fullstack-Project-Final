package com.devaemlak.order_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "ad_package")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @CreationTimestamp
    @Column(name = "createdDate", updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @CreationTimestamp
    @Column(name = "expiryDate")
    private LocalDateTime expiryDate;

}
