package com.devaemlak.advertisement_service.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageModel {
    private String name;
    private MultipartFile file;
    private Long advertisementId;
}
