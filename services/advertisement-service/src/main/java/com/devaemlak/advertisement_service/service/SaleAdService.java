package com.devaemlak.advertisement_service.service;

import com.devaemlak.advertisement_service.converter.AdvertisementConverter;
import com.devaemlak.advertisement_service.converter.RentalAdConverter;
import com.devaemlak.advertisement_service.converter.SaleAdConverter;
import com.devaemlak.advertisement_service.dto.request.AdvertisementUpdateStatusRequest;
import com.devaemlak.advertisement_service.dto.request.SaleAdSaveRequest;
import com.devaemlak.advertisement_service.dto.response.RentalAdResponse;
import com.devaemlak.advertisement_service.dto.response.SaleAdResponse;
import com.devaemlak.advertisement_service.model.Advertisement;
import com.devaemlak.advertisement_service.model.RentalAd;
import com.devaemlak.advertisement_service.model.SaleAd;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaleAdService {

    private final AdvertisementRepository advertisementRepository;

    @Transactional
    public void save(SaleAdSaveRequest request) {
        advertisementRepository.save(SaleAdConverter.toSaleAd(request));
    }

    @Transactional
    public void updateStatus(AdvertisementUpdateStatusRequest updateStatusRequest) {
        Advertisement foundedAdvertisement = advertisementRepository.findById(updateStatusRequest.getId())
                .orElseThrow(() -> new RuntimeException("Advertisement not found"));
        foundedAdvertisement.setAdvertisementStatus(updateStatusRequest.getStatus());
        advertisementRepository.save(foundedAdvertisement);
    }
    
    public List<SaleAdResponse> getAll() {
        List<SaleAd> saleAds = advertisementRepository.findAll().stream()
                .filter(ad -> ad instanceof SaleAd)
                .map(ad -> (SaleAd) ad)
                .toList();
        return SaleAdConverter.toResponse(saleAds);
    }

    public SaleAdResponse getById(Long id) {
        return getAll().stream()
                .filter(saleAdResponse -> saleAdResponse.getId().equals(id))
                .findFirst().orElse(null);
    }

    public List<SaleAdResponse> getByUserId(Long userId) {
        return getAll().stream()
                .filter(saleAdResponse -> saleAdResponse.getUserId().equals(userId))
                .toList();
    }

    public List<SaleAdResponse> getByStatus(Long userId, AdvertisementStatus status) {
        List<SaleAd> foundedAdvertisements = advertisementRepository.findAll().stream()
                .filter(ad -> ad instanceof SaleAd)
                .filter(saleAdResponse ->
                        saleAdResponse.getAdvertisementStatus().equals(status) && saleAdResponse.getUserId().equals(userId))
                .map(ad -> (SaleAd) ad)
                .toList();
        return SaleAdConverter.toResponse(foundedAdvertisements);
    }
}
