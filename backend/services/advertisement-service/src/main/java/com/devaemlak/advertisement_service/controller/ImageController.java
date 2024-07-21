package com.devaemlak.advertisement_service.controller;

import com.devaemlak.advertisement_service.model.Image;
import com.devaemlak.advertisement_service.model.ImageModel;
import com.devaemlak.advertisement_service.repository.ImageRepository;
import com.devaemlak.advertisement_service.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/attachments")
@RequiredArgsConstructor
public class ImageController {

    private final ImageRepository imageRepository;
    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> upload(@RequestParam("name") String name,
                                                      @RequestParam("file") MultipartFile file,
                                                      @RequestParam("advertisement-id") Long advertisementId) {
        ImageModel imageModel = new ImageModel();
        imageModel.setName(name);
        imageModel.setFile(file);
        imageModel.setAdvertisementId(advertisementId);
        return imageService.uploadImage(imageModel);
    }

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages() {
        return ResponseEntity.ok(imageService.getAllImages());
    }

}
