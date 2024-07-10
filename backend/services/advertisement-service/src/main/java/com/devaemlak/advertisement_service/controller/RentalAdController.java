package com.devaemlak.advertisement_service.controller;

import com.devaemlak.advertisement_service.dto.request.AdvertisementUpdateStatusRequest;
import com.devaemlak.advertisement_service.dto.request.RentalAdSaveRequest;
import com.devaemlak.advertisement_service.dto.request.SaleAdSaveRequest;
import com.devaemlak.advertisement_service.dto.response.AdvertisementResponse;
import com.devaemlak.advertisement_service.dto.response.GenericResponse;
import com.devaemlak.advertisement_service.dto.response.RentalAdResponse;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.service.RentalAdService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/rental-ads")
@RequiredArgsConstructor
public class RentalAdController {

    private final RentalAdService rentalAdService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody RentalAdSaveRequest request){
        rentalAdService.save(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public GenericResponse<List<RentalAdResponse>> getAll() {
        return GenericResponse.success(rentalAdService.getAll());
    }

    @GetMapping("/{id}")
    public GenericResponse<RentalAdResponse> getById(@PathVariable Long id) {
        RentalAdResponse advertisementResponse = rentalAdService.getById(id);
        return GenericResponse.success(advertisementResponse);
    }

    @GetMapping("/user/{id}")
    public GenericResponse<List<RentalAdResponse>> getByUserId(@PathVariable(name = "id") Long userId) {
        return GenericResponse.success(rentalAdService.getByUserId(userId));
    }

    @PutMapping
    public ResponseEntity<Void> updateStatus(@RequestBody AdvertisementUpdateStatusRequest updateStatusRequest){
        rentalAdService.updateStatus(updateStatusRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/status")
    public GenericResponse<List<RentalAdResponse>> getByStatusForUser(@RequestParam(name = "userId") Long userId, @RequestParam(name = "status") AdvertisementStatus status) {
        return GenericResponse.success(rentalAdService.getByStatus(userId, status));
    }
}
