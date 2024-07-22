package com.devaemlak.advertisement_service.controller;

import com.devaemlak.advertisement_service.dto.request.AdvertisementSaveRequest;
import com.devaemlak.advertisement_service.dto.request.AdvertisementUpdateStatusRequest;
import com.devaemlak.advertisement_service.dto.request.RentalAdUpdateRequesst;
import com.devaemlak.advertisement_service.dto.response.GenericResponse;
import com.devaemlak.advertisement_service.dto.response.RentalAdResponse;
import com.devaemlak.advertisement_service.exception.AdvertisementException;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.service.RentalAdService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RentalAdControllerTest {

    @Mock
    private RentalAdService rentalAdService;

    @InjectMocks
    private RentalAdController rentalAdController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_ShouldReturnCreatedResponse_WhenSuccessful() {
        AdvertisementSaveRequest request = new AdvertisementSaveRequest();
        RentalAdResponse response = new RentalAdResponse();
        when(rentalAdService.save(request)).thenReturn(response);

        ResponseEntity<RentalAdResponse> result = rentalAdController.save(request);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void getAll_ShouldReturnListOfRentalAdResponse_WhenSuccessful() {
        List<RentalAdResponse> responses = List.of(new RentalAdResponse(), new RentalAdResponse());
        when(rentalAdService.getAll()).thenReturn(responses);

        GenericResponse<List<RentalAdResponse>> result = rentalAdController.getAll();
        assertEquals(responses, result.getData());
    }

    @Test
    void getById_ShouldReturnRentalAdResponse_WhenFound() {
        Long id = 1L;
        RentalAdResponse response = new RentalAdResponse();
        when(rentalAdService.getById(id)).thenReturn(response);

        GenericResponse<RentalAdResponse> result = rentalAdController.getById(id);
        assertEquals(response, result.getData());
    }

    @Test
    void getByUserId_ShouldReturnListOfRentalAdResponse_WhenFound() {
        Long userId = 1L;
        List<RentalAdResponse> responses = List.of(new RentalAdResponse(), new RentalAdResponse());
        when(rentalAdService.getByUserId(userId)).thenReturn(responses);

        GenericResponse<List<RentalAdResponse>> result = rentalAdController.getByUserId(userId);
        assertEquals(responses, result.getData());
    }

    @Test
    void update_ShouldReturnOkResponse_WhenUpdateIsSuccessful() {
        RentalAdUpdateRequesst request = new RentalAdUpdateRequesst();
        Long id = 1L;
        RentalAdResponse response = new RentalAdResponse();
        when(rentalAdService.update(request, id)).thenReturn(response);

        ResponseEntity<RentalAdResponse> result = rentalAdController.update(request, id);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void update_ShouldReturnBadRequest_WhenExceptionOccurs() {
        RentalAdUpdateRequesst request = new RentalAdUpdateRequesst();
        Long id = 1L;
        when(rentalAdService.update(request, id)).thenThrow(new AdvertisementException("Error"));

        ResponseEntity<RentalAdResponse> result = rentalAdController.update(request, id);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void updateStatus_ShouldReturnOkResponse_WhenUpdateIsSuccessful() {
        AdvertisementUpdateStatusRequest request = new AdvertisementUpdateStatusRequest();
        RentalAdResponse response = new RentalAdResponse();
        when(rentalAdService.updateStatus(request)).thenReturn(response);

        ResponseEntity<RentalAdResponse> result = rentalAdController.updateStatus(request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void getByStatusForUser_ShouldReturnListOfRentalAdResponse_WhenFound() {
        Long userId = 1L;
        AdvertisementStatus status = AdvertisementStatus.ACTIVE;
        List<RentalAdResponse> responses = List.of(new RentalAdResponse(), new RentalAdResponse());
        when(rentalAdService.getByStatus(userId, status)).thenReturn(responses);

        GenericResponse<List<RentalAdResponse>> result = rentalAdController.getByStatusForUser(userId, status);
        assertEquals(responses, result.getData());
    }
}
