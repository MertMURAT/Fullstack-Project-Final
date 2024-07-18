package com.devaemlak.advertisement_service.controller;

import com.devaemlak.advertisement_service.dto.request.*;
import com.devaemlak.advertisement_service.dto.response.GenericResponse;
import com.devaemlak.advertisement_service.dto.response.SaleAdResponse;
import com.devaemlak.advertisement_service.exception.AdvertisementException;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.service.SaleAdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/sale-ads")
@RequiredArgsConstructor
public class SaleAdController {

    private final SaleAdService saleAdService;

    @PostMapping
    public ResponseEntity<SaleAdResponse> save(@RequestBody AdvertisementSaveRequest request) {
        SaleAdResponse saleAdResponse = saleAdService.save(request);
        return new ResponseEntity<>(saleAdResponse, HttpStatus.CREATED);
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

    @PutMapping("/{id}")
    public ResponseEntity<SaleAdResponse> update(@RequestBody SaleAdUpdateRequest request, @PathVariable Long id) {
        try {
            SaleAdResponse saleAdResponse = saleAdService.update(request, id);
            return new ResponseEntity<>(saleAdResponse, HttpStatus.OK);
        } catch (AdvertisementException e) {
            log.error("Error updating SaleAd: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public ResponseEntity<SaleAdResponse> updateStatus(@RequestBody AdvertisementUpdateStatusRequest updateStatusRequest) {
        SaleAdResponse saleAdResponse = saleAdService.updateStatus(updateStatusRequest);
        return new ResponseEntity<>(saleAdResponse,HttpStatus.OK);
    }

    @GetMapping("/status")
    public GenericResponse<List<SaleAdResponse>> getByStatusForUser(@RequestParam(name = "userId") Long userId, @RequestParam(name = "status") AdvertisementStatus status) {
        return GenericResponse.success(saleAdService.getByStatus(userId, status));
    }

}
