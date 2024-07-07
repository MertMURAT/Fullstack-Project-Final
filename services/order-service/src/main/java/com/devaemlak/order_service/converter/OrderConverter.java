package com.devaemlak.order_service.converter;

import com.devaemlak.order_service.dto.request.OrderSaveRequest;
import com.devaemlak.order_service.dto.response.OrderResponse;
import com.devaemlak.order_service.model.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderConverter {

    public static Order toOrder(OrderSaveRequest request){
        return Order.builder()
                .quantity(10)
                .userId(request.getUserId())
                .totalAmount(new BigDecimal(500))
                .build();
    }

    public static OrderResponse toResponse(Order order){
        return OrderResponse.builder()
                .id(order.getId())
                .quantity(order.getQuantity())
                .totalAmount(order.getTotalAmount())
                .userId(order.getUserId())
                .createdDate(order.getCreatedDate())
                .lastModifiedDate(order.getLastModifiedDate())
                .build();
    }

    public static List<OrderResponse> toResponse(List<Order> orders){
        return orders.stream()
                .map(OrderConverter::toResponse)
                .toList();
    }

}
