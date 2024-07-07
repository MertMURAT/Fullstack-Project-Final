package com.devaemlak.order_service.controller;

import com.devaemlak.order_service.converter.OrderConverter;
import com.devaemlak.order_service.dto.request.OrderSaveRequest;
import com.devaemlak.order_service.dto.response.GenericResponse;
import com.devaemlak.order_service.dto.response.OrderResponse;
import com.devaemlak.order_service.model.Order;
import com.devaemlak.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody OrderSaveRequest request){
        orderService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public GenericResponse<List<OrderResponse>> getAll(){
        return GenericResponse.success(orderService.getAll());
    }

    @GetMapping("/{id}")
    public GenericResponse<OrderResponse> getById(@PathVariable Long id){
        return GenericResponse.success(orderService.getById(id));
    }

}
