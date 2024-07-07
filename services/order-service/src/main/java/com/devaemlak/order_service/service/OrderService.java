package com.devaemlak.order_service.service;

import com.devaemlak.order_service.converter.OrderConverter;
import com.devaemlak.order_service.dto.request.OrderSaveRequest;
import com.devaemlak.order_service.dto.response.OrderResponse;
import com.devaemlak.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public void save(OrderSaveRequest request){
        orderRepository.save(OrderConverter.toOrder(request));
    }

    public List<OrderResponse> getAll(){
        return OrderConverter.toResponse(orderRepository.findAll());
    }

    public OrderResponse getById(Long id){
        return orderRepository.findAll()
                .stream()
                .map(OrderConverter::toResponse)
                .findFirst()
                .orElse(null);
    }
}
