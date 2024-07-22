package com.devaemlak.advertisement_service.controller;

import com.devaemlak.advertisement_service.model.Image;
import com.devaemlak.advertisement_service.model.ImageModel;
import com.devaemlak.advertisement_service.service.ImageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ImageControllerTest {

    @Mock
    private ImageService imageService;

    @InjectMocks
    private ImageController imageController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void upload_ShouldReturnResponseEntityFromService() {
        String name = "Test Image";
        MultipartFile file = mock(MultipartFile.class);
        Long advertisementId = 1L;

        Map<String, String> responseBody = Map.of("url", "http://image.url");
        when(imageService.uploadImage(any(ImageModel.class))).thenReturn(ResponseEntity.ok(responseBody));

        ResponseEntity<Map<String, String>> response = imageController.upload(name, file, advertisementId);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(responseBody, response.getBody());
    }

    @Test
    void getAllImages_ShouldReturnListOfImages() {
        Image image1 = new Image();
        Image image2 = new Image();
        when(imageService.getAllImages()).thenReturn(List.of(image1, image2));

        ResponseEntity<List<Image>> response = imageController.getAllImages();
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
    }
}
