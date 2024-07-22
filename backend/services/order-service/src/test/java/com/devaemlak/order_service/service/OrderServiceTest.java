package com.devaemlak.order_service.service;

import com.devaemlak.order_service.converter.OrderConverter;
import com.devaemlak.order_service.dto.request.OrderSaveRequest;
import com.devaemlak.order_service.dto.response.OrderResponse;
import com.devaemlak.order_service.exception.ExceptionMessages;
import com.devaemlak.order_service.exception.OrderException;
import com.devaemlak.order_service.model.Order;
import com.devaemlak.order_service.producer.LogProducer;
import com.devaemlak.order_service.producer.dto.LogDto;
import com.devaemlak.order_service.producer.dto.enums.LogType;
import com.devaemlak.order_service.producer.dto.enums.OperationType;
import com.devaemlak.order_service.repository.OrderRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private LogProducer logProducer;

    @Mock
    private PackageService packageService;

    @Test
    void save_successfully() {
        //given
        OrderSaveRequest request = Instancio.create(OrderSaveRequest.class);
        Order order = OrderConverter.toOrder(request);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        //when
        orderService.save(request);

        //then
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(logProducer, times(1)).sendToLog(any(LogDto.class));
        verify(packageService, times(1)).save();
    }

    @Test
    void shouldThrowException_whenSaveFails() {
        //given
        OrderSaveRequest request = Instancio.create(OrderSaveRequest.class);

        when(orderRepository.save(any(Order.class))).thenThrow(new RuntimeException());

        //when
        OrderException exception = Assertions.assertThrows(OrderException.class, () -> {
            orderService.save(request);
        });

        //then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.ORDER_SAVE_ERROR);
        verify(logProducer, times(1)).sendToLog(any(LogDto.class));
    }

    @Test
    void getAll_successfully() {
        //given
        List<Order> orders = List.of(Instancio.create(Order.class));
        when(orderRepository.findAll()).thenReturn(orders);

        //when
        List<OrderResponse> orderResponses = orderService.getAll();

        //then
        assertThat(orderResponses).isNotEmpty();
        verify(orderRepository, times(1)).findAll();
        verify(logProducer, times(1)).sendToLog(any(LogDto.class));
    }

    @Test
    void shouldThrowException_whenGetAllFails() {
        //given
        when(orderRepository.findAll()).thenThrow(new RuntimeException());

        //when
        OrderException exception = Assertions.assertThrows(OrderException.class, () -> {
            orderService.getAll();
        });

        //then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.ORDER_RETRIEVE_ERROR);
        verify(logProducer, times(1)).sendToLog(any(LogDto.class));
    }

    @Test
    void getById_successfully() {
        //given
        Long orderId = 1L;
        Order order = Instancio.create(Order.class);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        //when
        OrderResponse orderResponse = orderService.getById(orderId);

        //then
        assertThat(orderResponse).isNotNull();
        verify(orderRepository, times(1)).findById(orderId);
        verify(logProducer, times(1)).sendToLog(any(LogDto.class));
    }

    @Test
    void shouldThrowException_whenOrderNotFound() {
        //given
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        //when
        OrderException exception = Assertions.assertThrows(OrderException.class, () -> {
            orderService.getById(orderId);
        });

        //then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.ORDER_NOT_FOUND);
        verify(logProducer, times(1)).sendToLog(any(LogDto.class));
    }
}