package com.devaemlak.advertisement_service.service;

import com.devaemlak.advertisement_service.converter.AdvertisementConverter;
import com.devaemlak.advertisement_service.converter.RentalAdConverter;
import com.devaemlak.advertisement_service.converter.SaleAdConverter;
import com.devaemlak.advertisement_service.dto.request.AdvertisementUpdateStatusRequest;
import com.devaemlak.advertisement_service.dto.request.SaleAdSaveRequest;
import com.devaemlak.advertisement_service.dto.response.RentalAdResponse;
import com.devaemlak.advertisement_service.dto.response.SaleAdResponse;
import com.devaemlak.advertisement_service.exception.AdvertisementException;
import com.devaemlak.advertisement_service.exception.ExceptionMessages;
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
        try {
            advertisementRepository.save(SaleAdConverter.toSaleAd(request));
        } catch (Exception e) {
            log.error("İlan kaydedilirken hata oluştu: {}", e.getMessage());
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_SAVE_ERROR);
        }
    }

    @Transactional
    public void updateStatus(AdvertisementUpdateStatusRequest updateStatusRequest) {
        try {
            Advertisement foundedAdvertisement = advertisementRepository.findById(updateStatusRequest.getId())
                    .orElseThrow(() -> new AdvertisementException(ExceptionMessages.ADVERTISEMENT_NOT_FOUND));
            foundedAdvertisement.setAdvertisementStatus(updateStatusRequest.getStatus());
            advertisementRepository.save(foundedAdvertisement);
        } catch (AdvertisementException e) {
            throw e;
        } catch (Exception e) {
            log.error("İlan güncellenirken hata oluştu: {}", e.getMessage());
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_UPDATE_ERROR);
        }
    }

    public List<SaleAdResponse> getAll() {
        try {
            List<SaleAd> saleAds = advertisementRepository.findAll().stream()
                    .filter(ad -> ad instanceof SaleAd)
                    .map(ad -> (SaleAd) ad)
                    .toList();
            return SaleAdConverter.toResponse(saleAds);
        } catch (Exception e) {
            log.error("Tüm satış ilanları çekilirken hata oluştu: {}", e.getMessage());
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR);
        }
    }

    public SaleAdResponse getById(Long id) {
        return advertisementRepository.findById(id)
                .map(ad -> (SaleAd) ad)
                .map(SaleAdConverter::toResponse)
                .orElseThrow(() -> new AdvertisementException(ExceptionMessages.ADVERTISEMENT_NOT_FOUND));
    }

    public List<SaleAdResponse> getByUserId(Long userId) {
        return getAll().stream()
                .filter(saleAdResponse -> saleAdResponse.getUserId().equals(userId))
                .toList();
    }

    public List<SaleAdResponse> getByStatus(Long userId, AdvertisementStatus status) {
        try {
            List<SaleAd> foundedAdvertisements = advertisementRepository.findAll().stream()
                    .filter(ad -> ad instanceof SaleAd)
                    .filter(saleAdResponse ->
                            saleAdResponse.getAdvertisementStatus().equals(status) && saleAdResponse.getUserId().equals(userId))
                    .map(ad -> (SaleAd) ad)
                    .toList();
            return SaleAdConverter.toResponse(foundedAdvertisements);
        } catch (Exception e) {
            log.error("Satış ilanları durumuna göre getirirken hata oluştu: {}", e.getMessage());
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR);
        }
    }
}
