package com.devaemlak.order_service.controller;

import com.devaemlak.order_service.dto.request.OrderSaveRequest;
import com.devaemlak.order_service.dto.response.GenericResponse;
import com.devaemlak.order_service.dto.response.OrderResponse;
import com.devaemlak.order_service.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void save_successfully() throws Exception {
        // given
        OrderSaveRequest request = new OrderSaveRequest();
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(request);

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/orders")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isCreated());
        verify(orderService, times(1)).save(Mockito.any(OrderSaveRequest.class));
    }

    @Test
    void getAll_successfully() throws Exception {
        // given
        OrderResponse orderResponse = new OrderResponse();
        List<OrderResponse> orderResponses = Collections.singletonList(orderResponse);
        GenericResponse<List<OrderResponse>> response = GenericResponse.success(orderResponses);

        when(orderService.getAll()).thenReturn(orderResponses);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/orders")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
        verify(orderService, times(1)).getAll();
    }

    @Test
    void getById_successfully() throws Exception {
        // given
        Long orderId = 1L;
        OrderResponse orderResponse = new OrderResponse();
        GenericResponse<OrderResponse> response = GenericResponse.success(orderResponse);

        when(orderService.getById(orderId)).thenReturn(orderResponse);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/orders/{id}", orderId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
        verify(orderService, times(1)).getById(orderId);
    }
}