package com.devaemlak.order_service.controller;

import com.devaemlak.order_service.dto.response.AdPackageResponse;
import com.devaemlak.order_service.dto.response.GenericResponse;
import com.devaemlak.order_service.dto.response.OrderResponse;
import com.devaemlak.order_service.model.AdPackage;
import com.devaemlak.order_service.service.PackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/ad-packages")
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;

    @PutMapping
    public ResponseEntity<Void> decreaseByOne(){
        packageService.decreaseByOne();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public GenericResponse<List<AdPackageResponse>> getAll(){
        return GenericResponse.success(packageService.getAll());
    }

    @GetMapping("/total-quantity")
    public GenericResponse<Integer> getTotalQuantity(){
        return GenericResponse.success(packageService.getTotalQuantity());
    }
}
