package com.devaemlak.order_service.dto.response;

import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdPackageResponse {

    private Integer quantity;
    private LocalDateTime createdDate;
    private LocalDateTime expiryDate;

}
