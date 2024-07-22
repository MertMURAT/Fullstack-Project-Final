package com.devaemlak.advertisement_service.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.Url;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CloudinaryServiceTest {

    @Mock
    private Cloudinary cloudinary;

    @InjectMocks
    private CloudinaryService cloudinaryService;

    @Mock
    private MultipartFile multipartFile;

    @Mock
    private Uploader uploader;

    @Mock
    private Url cloudinaryUrl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadFile_ShouldReturnUrl_WhenUploadIsSuccessful() throws IOException {
        when(cloudinary.uploader()).thenReturn(uploader);
        when(multipartFile.getBytes()).thenReturn(new byte[0]);

        Map<String, String> uploadResult = Map.of(
                "public_id", "sample_id"
        );
        when(uploader.upload(any(byte[].class), any(Map.class))).thenReturn(uploadResult);
        when(cloudinary.url()).thenReturn(cloudinaryUrl);
        when(cloudinaryUrl.secure(true)).thenReturn(cloudinaryUrl);
        when(cloudinaryUrl.generate("sample_id")).thenReturn("https://res.cloudinary.com/sample_id");

        String result = cloudinaryService.uploadFile(multipartFile, "test_folder");

        assertNotNull(result);
        assertEquals("https://res.cloudinary.com/sample_id", result);
    }

    @Test
    void uploadFile_ShouldReturnNull_WhenIOExceptionOccurs() throws IOException {
        when(cloudinary.uploader()).thenReturn(uploader);
        when(multipartFile.getBytes()).thenThrow(new IOException("Test IOException"));

        String result = cloudinaryService.uploadFile(multipartFile, "test_folder");

        assertNull(result);
    }
}
