package com.devaemlak.advertisement_service.service;

import com.devaemlak.advertisement_service.converter.RentalAdConverter;
import com.devaemlak.advertisement_service.dto.request.AdvertisementSaveRequest;
import com.devaemlak.advertisement_service.dto.request.AdvertisementUpdateStatusRequest;
import com.devaemlak.advertisement_service.dto.request.RentalAdUpdateRequesst;
import com.devaemlak.advertisement_service.dto.response.RentalAdResponse;
import com.devaemlak.advertisement_service.exception.AdvertisementException;
import com.devaemlak.advertisement_service.exception.ExceptionMessages;
import com.devaemlak.advertisement_service.model.Advertisement;
import com.devaemlak.advertisement_service.model.RentalAd;
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
public class RentalAdService {

    private final AdvertisementRepository advertisementRepository;

    @Transactional
    public RentalAdResponse save(AdvertisementSaveRequest request) {
        try {
            RentalAd rentalAd = RentalAdConverter.toRentalInit(request);
            advertisementRepository.save(rentalAd);
            return RentalAdConverter.toResponse(rentalAd);
        } catch (Exception e) {
            log.error("İlan kaydedilirken hata oluştu: {}", e.getMessage());
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_SAVE_ERROR);
        }
    }

    @Transactional
    public RentalAdResponse update(RentalAdUpdateRequesst request, Long id) {
        try {
            Optional<Advertisement> foundedAd = advertisementRepository.findById(id);
            if (foundedAd.isPresent()) {
                Advertisement ad = foundedAd.get();
                if (!(ad instanceof RentalAd updatedRentalAd)) {
                    throw new AdvertisementException("Advertisement is not of type RentalAd.");
                }
                RentalAd rentalAd = RentalAdConverter.toRentalAd(request);
                RentalAdConverter.updateAdFromRentalAd(updatedRentalAd, rentalAd);
                RentalAd saveAd = advertisementRepository.save(updatedRentalAd);
                return RentalAdConverter.toResponse(saveAd);
            }
            return null;
        } catch (Exception e) {
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_UPDATE_ERROR);
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

    public List<RentalAdResponse> getAll() {
        try {
            List<RentalAd> rentalAds = advertisementRepository.findAll().stream()
                    .filter(ad -> ad instanceof RentalAd)
                    .map(ad -> (RentalAd) ad)
                    .toList();
            return RentalAdConverter.toResponse(rentalAds);
        } catch (Exception e) {
            log.error("Tüm kiralık ilanlar çekilirken hata oluştu: {}", e.getMessage());
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR);
        }
    }

    public RentalAdResponse getById(Long id) {
        return advertisementRepository.findById(id)
                .filter(ad -> ad instanceof RentalAd)
                .map(ad -> (RentalAd) ad)
                .map(RentalAdConverter::toResponse)
                .orElseThrow(() -> new AdvertisementException(ExceptionMessages.ADVERTISEMENT_NOT_FOUND));
    }

    public List<RentalAdResponse> getByUserId(Long userId) {
        return getAll().stream()
                .filter(rentalAdResponse -> rentalAdResponse.getUserId().equals(userId))
                .toList();
    }

    public List<RentalAdResponse> getByStatus(Long userId, AdvertisementStatus status) {
        try {
            List<RentalAd> foundedAdvertisements = advertisementRepository.findAll().stream()
                    .filter(ad -> ad instanceof RentalAd)
                    .filter(rentalAdResponse ->
                            rentalAdResponse.getAdvertisementStatus().equals(status) && rentalAdResponse.getUserId().equals(userId))
                    .map(ad -> (RentalAd) ad)
                    .toList();
            return RentalAdConverter.toResponse(foundedAdvertisements);
        } catch (Exception e) {
            log.error("Kiralık ilanları durumuna göre getirirken hata oluştu: {}", e.getMessage());
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR);
        }
    }
}
