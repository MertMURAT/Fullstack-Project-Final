package com.devaemlak.advertisement_service.controller;

import com.devaemlak.advertisement_service.dto.request.AdvertisementSaveRequest;
import com.devaemlak.advertisement_service.dto.request.AdvertisementUpdateStatusRequest;
import com.devaemlak.advertisement_service.dto.request.SaleAdUpdateRequest;
import com.devaemlak.advertisement_service.dto.response.GenericResponse;
import com.devaemlak.advertisement_service.dto.response.SaleAdResponse;
import com.devaemlak.advertisement_service.exception.AdvertisementException;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.service.SaleAdService;
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

class SaleAdControllerTest {

    @Mock
    private SaleAdService saleAdService;

    @InjectMocks
    private SaleAdController saleAdController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_ShouldReturnCreatedResponse_WhenSuccessful() {
        AdvertisementSaveRequest request = new AdvertisementSaveRequest();
        SaleAdResponse response = new SaleAdResponse();
        when(saleAdService.save(request)).thenReturn(response);

        ResponseEntity<SaleAdResponse> result = saleAdController.save(request);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void getAll_ShouldReturnListOfSaleAdResponse_WhenSuccessful() {
        List<SaleAdResponse> responses = List.of(new SaleAdResponse(), new SaleAdResponse());
        when(saleAdService.getAll()).thenReturn(responses);

        GenericResponse<List<SaleAdResponse>> result = saleAdController.getAll();
        assertEquals(responses, result.getData());
    }

    @Test
    void getById_ShouldReturnSaleAdResponse_WhenFound() {
        Long id = 1L;
        SaleAdResponse response = new SaleAdResponse();
        when(saleAdService.getById(id)).thenReturn(response);

        GenericResponse<SaleAdResponse> result = saleAdController.getById(id);
        assertEquals(response, result.getData());
    }

    @Test
    void getByUserId_ShouldReturnListOfSaleAdResponse_WhenFound() {
        Long userId = 1L;
        List<SaleAdResponse> responses = List.of(new SaleAdResponse(), new SaleAdResponse());
        when(saleAdService.getByUserId(userId)).thenReturn(responses);

        GenericResponse<List<SaleAdResponse>> result = saleAdController.getByUserId(userId);
        assertEquals(responses, result.getData());
    }

    @Test
    void update_ShouldReturnOkResponse_WhenUpdateIsSuccessful() {
        SaleAdUpdateRequest request = new SaleAdUpdateRequest();
        Long id = 1L;
        SaleAdResponse response = new SaleAdResponse();
        when(saleAdService.update(request, id)).thenReturn(response);

        ResponseEntity<SaleAdResponse> result = saleAdController.update(request, id);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void update_ShouldReturnBadRequest_WhenExceptionOccurs() {
        SaleAdUpdateRequest request = new SaleAdUpdateRequest();
        Long id = 1L;
        when(saleAdService.update(request, id)).thenThrow(new AdvertisementException("Error"));

        ResponseEntity<SaleAdResponse> result = saleAdController.update(request, id);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertNull(result.getBody());
    }

    @Test
    void updateStatus_ShouldReturnOkResponse_WhenUpdateIsSuccessful() {
        AdvertisementUpdateStatusRequest request = new AdvertisementUpdateStatusRequest();
        SaleAdResponse response = new SaleAdResponse();
        when(saleAdService.updateStatus(request)).thenReturn(response);

        ResponseEntity<SaleAdResponse> result = saleAdController.updateStatus(request);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void getByStatusForUser_ShouldReturnListOfSaleAdResponse_WhenFound() {
        Long userId = 1L;
        AdvertisementStatus status = AdvertisementStatus.ACTIVE;
        List<SaleAdResponse> responses = List.of(new SaleAdResponse(), new SaleAdResponse());
        when(saleAdService.getByStatus(userId, status)).thenReturn(responses);

        GenericResponse<List<SaleAdResponse>> result = saleAdController.getByStatusForUser(userId, status);
        assertEquals(responses, result.getData());
    }
}
