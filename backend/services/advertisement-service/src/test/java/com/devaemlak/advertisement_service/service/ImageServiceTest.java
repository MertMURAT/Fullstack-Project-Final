package com.devaemlak.advertisement_service.service;

import com.devaemlak.advertisement_service.exception.AdvertisementException;
import com.devaemlak.advertisement_service.exception.ExceptionMessages;
import com.devaemlak.advertisement_service.model.Advertisement;
import com.devaemlak.advertisement_service.model.Image;
import com.devaemlak.advertisement_service.model.ImageModel;
import com.devaemlak.advertisement_service.repository.AdvertisementRepository;
import com.devaemlak.advertisement_service.repository.ImageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ImageServiceTest {

    @Mock
    private CloudinaryService cloudinaryService;

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private AdvertisementRepository advertisementRepository;

    @InjectMocks
    private ImageService imageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadImage_ShouldReturnBadRequest_WhenNameOrFileIsEmpty() {
        ImageModel imageModel = new ImageModel();
        imageModel.setName("");
        imageModel.setFile(mock(MultipartFile.class));
        imageModel.setAdvertisementId(1L);

        Advertisement advertisement = new Advertisement();
        when(advertisementRepository.findById(1L)).thenReturn(Optional.of(advertisement));

        ResponseEntity<Map<String, String>> response = imageService.uploadImage(imageModel);
        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().containsKey("error"));
    }

    @Test
    void uploadImage_ShouldReturnOk_WhenImageIsUploadedSuccessfully() {
        ImageModel imageModel = new ImageModel();
        imageModel.setName("Test Image");
        imageModel.setFile(mock(MultipartFile.class));
        imageModel.setAdvertisementId(1L);

        Advertisement advertisement = new Advertisement();
        when(advertisementRepository.findById(1L)).thenReturn(Optional.of(advertisement));
        when(cloudinaryService.uploadFile(any(MultipartFile.class), eq("deva_emlak"))).thenReturn("http://image.url");

        ResponseEntity<Map<String, String>> response = imageService.uploadImage(imageModel);
        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().containsKey("url"));
    }

    @Test
    void uploadImage_ShouldReturnInternalServerError_WhenExceptionOccurs() {
        ImageModel imageModel = new ImageModel();
        imageModel.setName("Test Image");
        imageModel.setFile(mock(MultipartFile.class));
        imageModel.setAdvertisementId(1L);

        Advertisement advertisement = new Advertisement();
        when(advertisementRepository.findById(1L)).thenReturn(Optional.of(advertisement));
        when(cloudinaryService.uploadFile(any(MultipartFile.class), eq("deva_emlak"))).thenThrow(new RuntimeException("Cloudinary error"));

        ResponseEntity<Map<String, String>> response = imageService.uploadImage(imageModel);
        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().containsKey("error"));
    }

    @Test
    void getAllImages_ShouldReturnListOfImages() {
        Image image1 = new Image();
        Image image2 = new Image();
        when(imageRepository.findAll()).thenReturn(List.of(image1, image2));

        List<Image> images = imageService.getAllImages();
        assertEquals(2, images.size());
    }

    @Test
    void getAllImages_ShouldThrowAdvertisementException_WhenExceptionOccurs() {
        when(imageRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        assertThrows(AdvertisementException.class, () -> imageService.getAllImages());
    }
}
