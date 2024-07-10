package com.devaemlak.order_service.service;

import com.devaemlak.order_service.converter.OrderConverter;
import com.devaemlak.order_service.dto.request.OrderSaveRequest;
import com.devaemlak.order_service.dto.response.OrderResponse;
import com.devaemlak.order_service.exception.ExceptionMessages;
import com.devaemlak.order_service.exception.OrderException;
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
    public void save(OrderSaveRequest request) {
        try {
            orderRepository.save(OrderConverter.toOrder(request));
        } catch (Exception e) {
            log.error("Sipariş kaydedilirken hata oluştu: {}", e.getMessage());
            throw new OrderException(ExceptionMessages.ORDER_SAVE_ERROR);
        }
    }

    public List<OrderResponse> getAll() {
        try {
            return OrderConverter.toResponse(orderRepository.findAll());
        } catch (Exception e) {
            log.error("Tüm siparişler alınırken hata oluştu: {}", e.getMessage());
            throw new OrderException(ExceptionMessages.ORDER_RETRIEVE_ERROR);
        }
    }

    public OrderResponse getById(Long id) {
        return orderRepository.findById(id)
                .map(OrderConverter::toResponse)
                .orElseThrow(() -> new OrderException(ExceptionMessages.ORDER_NOT_FOUND));
    }
}
