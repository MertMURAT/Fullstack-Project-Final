package com.devaemlak.advertisement_service.service;

import com.devaemlak.advertisement_service.converter.AdvertisementConverter;
import com.devaemlak.advertisement_service.converter.RentalAdConverter;
import com.devaemlak.advertisement_service.dto.request.AdvertisementUpdateStatusRequest;
import com.devaemlak.advertisement_service.dto.request.RentalAdSaveRequest;
import com.devaemlak.advertisement_service.dto.request.SaleAdSaveRequest;
import com.devaemlak.advertisement_service.dto.response.RentalAdResponse;
import com.devaemlak.advertisement_service.model.Advertisement;
import com.devaemlak.advertisement_service.model.RentalAd;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RentalAdService {

    private final AdvertisementRepository advertisementRepository;

    @Transactional
    public void save(RentalAdSaveRequest request) {
        advertisementRepository.save(RentalAdConverter.toRentalAd(request));
    }

    @Transactional
    public void updateStatus(AdvertisementUpdateStatusRequest updateStatusRequest) {
        Advertisement foundedAdvertisement = advertisementRepository.findById(updateStatusRequest.getId())
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));
        foundedAdvertisement.setAdvertisementStatus(updateStatusRequest.getStatus());
        advertisementRepository.save(foundedAdvertisement);
    }

    public List<RentalAdResponse> getAll() {
        List<RentalAd> rentalAds = advertisementRepository.findAll().stream()
                .filter(ad -> ad instanceof RentalAd)
                .map(ad -> (RentalAd) ad)
                .toList();
        return RentalAdConverter.toResponse(rentalAds);
    }

        public RentalAdResponse getById(Long id) {
        return getAll().stream()
                .filter(rentalAdResponse -> rentalAdResponse.getId().equals(id))
                .findFirst().orElse(null);
    }

    public List<RentalAdResponse> getByUserId(Long userId) {
        return getAll().stream()
                .filter(rentalAdResponse -> rentalAdResponse.getUserId().equals(userId))
                .toList();
    }

    public List<RentalAdResponse> getByStatus(Long userId, AdvertisementStatus status) {
        List<RentalAd> foundedAdvertisements = advertisementRepository.findAll().stream()
                .filter(ad -> ad instanceof RentalAd)
                .filter(rentalAdResponse ->
                        rentalAdResponse.getAdvertisementStatus().equals(status) && rentalAdResponse.getUserId().equals(userId))
                .map(ad -> (RentalAd) ad)
                .toList();
        return RentalAdConverter.toResponse(foundedAdvertisements);
    }
}
