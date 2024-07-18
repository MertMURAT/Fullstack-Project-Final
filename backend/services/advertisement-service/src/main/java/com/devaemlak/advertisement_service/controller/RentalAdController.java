package com.devaemlak.advertisement_service.controller;

import com.devaemlak.advertisement_service.dto.request.AdvertisementSaveRequest;
import com.devaemlak.advertisement_service.dto.request.AdvertisementUpdateStatusRequest;
import com.devaemlak.advertisement_service.dto.request.RentalAdUpdateRequesst;
import com.devaemlak.advertisement_service.dto.request.SaleAdUpdateRequest;
import com.devaemlak.advertisement_service.dto.response.GenericResponse;
import com.devaemlak.advertisement_service.dto.response.RentalAdResponse;
import com.devaemlak.advertisement_service.dto.response.SaleAdResponse;
import com.devaemlak.advertisement_service.exception.AdvertisementException;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.service.RentalAdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/rental-ads")
@RequiredArgsConstructor
public class RentalAdController {

    private final RentalAdService rentalAdService;

    @PostMapping
    public ResponseEntity<RentalAdResponse> save(@RequestBody AdvertisementSaveRequest request){
        RentalAdResponse rentalAdResponse = rentalAdService.save(request);
        return new ResponseEntity<>(rentalAdResponse, HttpStatus.CREATED);
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

    @PutMapping("/{id}")
    public ResponseEntity<RentalAdResponse> update(@RequestBody RentalAdUpdateRequesst request, @PathVariable Long id) {
        try {
            RentalAdResponse rentalAdResponse = rentalAdService.update(request, id);
            return new ResponseEntity<>(rentalAdResponse, HttpStatus.OK);
        } catch (AdvertisementException e) {
            log.error("Error updating RentalAd: {}", e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
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
