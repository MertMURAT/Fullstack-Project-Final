package com.devaemlak.advertisement_service.service;

import com.devaemlak.advertisement_service.exception.AdvertisementException;
import com.devaemlak.advertisement_service.exception.ExceptionMessages;
import com.devaemlak.advertisement_service.model.Advertisement;
import com.devaemlak.advertisement_service.model.Image;
import com.devaemlak.advertisement_service.model.ImageModel;
import com.devaemlak.advertisement_service.repository.AdvertisementRepository;
import com.devaemlak.advertisement_service.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;


import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {

    private final CloudinaryService cloudinaryService;
    private final ImageRepository imageRepository;
    private final AdvertisementRepository advertisementRepository;

    public ResponseEntity<Map<String, String>> uploadImage(ImageModel imageModel) {

        Advertisement advertisement = advertisementRepository.findById(imageModel.getAdvertisementId())
                .orElseThrow(() -> new AdvertisementException(ExceptionMessages.ADVERTISEMENT_NOT_FOUND));

        try {
            if (imageModel.getName().isEmpty() || imageModel.getFile().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "Name and file cannot be empty"));
            }
            Image image = new Image();
            image.setName(imageModel.getName());
            image.setImageUrl(cloudinaryService.uploadFile(imageModel.getFile(), "deva_emlak"));
            image.setAdvertisement(advertisement);
            if (image.getImageUrl() == null) {
                return ResponseEntity.badRequest().body(Map.of("error", "Image URL is null"));
            }
            imageRepository.save(image);
            return ResponseEntity.ok().body(Map.of("url", image.getImageUrl()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Internal Server Error"));
        }
    }

    public List<Image> getAllImages() {
        try {
            return imageRepository.findAll();
        } catch (Exception e) {
            throw new AdvertisementException("Error retrieving images");
        }
    }
}
