package com.devaemlak.order_service.service;

import com.devaemlak.order_service.converter.OrderConverter;
import com.devaemlak.order_service.dto.request.OrderSaveRequest;
import com.devaemlak.order_service.dto.response.OrderResponse;
import com.devaemlak.order_service.exception.ExceptionMessages;
import com.devaemlak.order_service.exception.OrderException;
import com.devaemlak.order_service.producer.LogProducer;
import com.devaemlak.order_service.producer.dto.LogDto;
import com.devaemlak.order_service.producer.dto.enums.LogType;
import com.devaemlak.order_service.producer.dto.enums.OperationType;
import com.devaemlak.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final LogProducer logProducer;

    @Transactional
    public void save(OrderSaveRequest request) {
        try {
            orderRepository.save(OrderConverter.toOrder(request));
            logProducer.sendToLog(prepareLogDto(OperationType.INSERT, ExceptionMessages.ORDER_SAVED, LogType.INFO));
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.INSERT, ExceptionMessages.ORDER_SAVE_ERROR, LogType.ERROR));
            throw new OrderException(ExceptionMessages.ORDER_SAVE_ERROR);
        }
    }

    public List<OrderResponse> getAll() {
        try {
            List<OrderResponse> orderResponses = OrderConverter.toResponse(orderRepository.findAll());
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ORDER_RETRIEVED, LogType.INFO));
            return orderResponses;
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ORDER_RETRIEVE_ERROR, LogType.ERROR));
            throw new OrderException(ExceptionMessages.ORDER_RETRIEVE_ERROR);
        }
    }

    public OrderResponse getById(Long id) {
        OrderResponse orderResponse = orderRepository.findById(id)
                .map(OrderConverter::toResponse)
                .orElseThrow(() -> new OrderException(ExceptionMessages.ORDER_NOT_FOUND));
        logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ORDER_RETRIEVED, LogType.INFO));
        return orderResponse;
    }

    private LogDto prepareLogDto(OperationType operationType, String message, LogType logType) {
        return LogDto.builder()
                .serviceName("order-service")
                .operationType(operationType)
                .logType(logType)
                .message(message)
                .timestamp(LocalDateTime.now())
                .exception("Order Exception")
                .build();
    }
}
