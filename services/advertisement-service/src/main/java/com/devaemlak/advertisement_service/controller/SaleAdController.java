package com.devaemlak.advertisement_service.controller;

import com.devaemlak.advertisement_service.dto.request.AdvertisementUpdateStatusRequest;
import com.devaemlak.advertisement_service.dto.request.RentalAdSaveRequest;
import com.devaemlak.advertisement_service.dto.request.SaleAdSaveRequest;
import com.devaemlak.advertisement_service.dto.response.AdvertisementResponse;
import com.devaemlak.advertisement_service.dto.response.GenericResponse;
import com.devaemlak.advertisement_service.dto.response.RentalAdResponse;
import com.devaemlak.advertisement_service.dto.response.SaleAdResponse;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.service.RentalAdService;
import com.devaemlak.advertisement_service.service.SaleAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sale-ads")
@RequiredArgsConstructor
public class SaleAdController {

    private final SaleAdService saleAdService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody SaleAdSaveRequest request){
        saleAdService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public GenericResponse<List<SaleAdResponse>> getAll() {
        return GenericResponse.success(saleAdService.getAll());
    }

    @GetMapping("/{id}")
    public GenericResponse<SaleAdResponse> getById(@PathVariable Long id) {
        SaleAdResponse advertisementResponse = saleAdService.getById(id);
        return GenericResponse.success(advertisementResponse);
    }

    @GetMapping("/user/{id}")
    public GenericResponse<List<SaleAdResponse>> getByUserId(@PathVariable(name = "id") Long userId) {
        return GenericResponse.success(saleAdService.getByUserId(userId));
    }

    @PutMapping
    public ResponseEntity<Void> updateStatus(@RequestBody AdvertisementUpdateStatusRequest updateStatusRequest){
        saleAdService.updateStatus(updateStatusRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/status")
    public GenericResponse<List<SaleAdResponse>> getByStatusForUser(@RequestParam(name = "userId") Long userId, @RequestParam(name = "status") AdvertisementStatus status) {
        return GenericResponse.success(saleAdService.getByStatus(userId, status));
    }

}
