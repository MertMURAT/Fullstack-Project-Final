package com.devaemlak.advertisement_service.service;

import com.devaemlak.advertisement_service.converter.SaleAdConverter;
import com.devaemlak.advertisement_service.dto.request.AdvertisementUpdateStatusRequest;
import com.devaemlak.advertisement_service.dto.request.AdvertisementSaveRequest;
import com.devaemlak.advertisement_service.dto.request.SaleAdUpdateRequest;
import com.devaemlak.advertisement_service.dto.response.SaleAdResponse;
import com.devaemlak.advertisement_service.exception.AdvertisementException;
import com.devaemlak.advertisement_service.exception.ExceptionMessages;
import com.devaemlak.advertisement_service.model.Advertisement;
import com.devaemlak.advertisement_service.model.SaleAd;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.repository.AdvertisementRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class SaleAdService {

    private final AdvertisementRepository advertisementRepository;

    @Transactional
    public SaleAdResponse save(AdvertisementSaveRequest request) {
        try {
            SaleAd saleAd = SaleAdConverter.toSaleInit(request);
            advertisementRepository.save(saleAd);

            return SaleAdConverter.toResponse(saleAd);
        } catch (Exception e) {
            log.error("İlan oluşturulurken hata oluştu: {}", e.getMessage());
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_SAVE_ERROR);
        }
    }

    @Transactional
    public SaleAdResponse update(SaleAdUpdateRequest request, Long id) {
        try {
            Optional<Advertisement> foundedAd = advertisementRepository.findById(id);
            if (foundedAd.isPresent()) {
                Advertisement ad = foundedAd.get();
                if (!(ad instanceof SaleAd updatedSaleAd)) {
                    throw new AdvertisementException("Advertisement is not of type SaleAd.");
                }
                SaleAd saleAd = SaleAdConverter.toSaleAd(request);
                SaleAdConverter.updateAdFromSaleAd(updatedSaleAd, saleAd);
                SaleAd saveAd = advertisementRepository.save(updatedSaleAd);

                return SaleAdConverter.toResponse(saveAd);
            }
            return null;
        } catch (Exception e) {
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_UPDATE_ERROR);
        }
    }

    @Transactional
    public SaleAdResponse updateStatus(AdvertisementUpdateStatusRequest updateStatusRequest) {
        try {
            Advertisement foundedAdvertisement = advertisementRepository.findById(updateStatusRequest.getId())
                    .orElseThrow(() -> new AdvertisementException(ExceptionMessages.ADVERTISEMENT_NOT_FOUND));
            foundedAdvertisement.setAdvertisementStatus(updateStatusRequest.getStatus());
            Advertisement advertisement = advertisementRepository.save(foundedAdvertisement);
            return SaleAdConverter.toResponse((SaleAd) advertisement);
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
                .filter(ad -> ad instanceof SaleAd)
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
