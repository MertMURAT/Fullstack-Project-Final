package com.devaemlak.advertisement_service.controller;

import com.devaemlak.advertisement_service.dto.request.AdvertisementSaveRequest;
import com.devaemlak.advertisement_service.dto.response.AdvertisementResponse;
import com.devaemlak.advertisement_service.dto.response.GenericResponse;
import com.devaemlak.advertisement_service.model.Advertisement;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.model.enums.AdvertisementType;
import com.devaemlak.advertisement_service.model.enums.HousingType;
import com.devaemlak.advertisement_service.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping
    public ResponseEntity<Advertisement> save(@RequestBody AdvertisementSaveRequest request) {
        Advertisement advertisement = advertisementService.save(request);
        return new ResponseEntity<>(advertisement, HttpStatus.CREATED);
    }

    @GetMapping
    public GenericResponse<List<Advertisement>> getAll() {
        return GenericResponse.success(advertisementService.getAll());
    }

    @GetMapping("/latest")
    public GenericResponse<List<Advertisement>> getLatestListing() {
        return GenericResponse.success(advertisementService.getAllByActive());
    }

    @GetMapping("/search")
    public GenericResponse<List<Advertisement>> searchAdvertisements(
            @RequestParam(required = false) AdvertisementType type,
            @RequestParam(required = false) int area,
            @RequestParam(required = false) int numberOfRooms,
            @RequestParam(required = false) int floorNumber,
            @RequestParam String searchTerm,
            @RequestParam(required = false) HousingType homeType) {

        return GenericResponse.success(advertisementService.search(type, area, numberOfRooms, floorNumber, searchTerm, homeType));
    }
}
