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
import com.devaemlak.advertisement_service.producer.LogProducer;
import com.devaemlak.advertisement_service.producer.dto.LogDto;
import com.devaemlak.advertisement_service.producer.dto.enums.LogType;
import com.devaemlak.advertisement_service.producer.dto.enums.OperationType;
import com.devaemlak.advertisement_service.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalAdService {

    private final AdvertisementRepository advertisementRepository;
    private final LogProducer logProducer;

    @Transactional
    public RentalAdResponse save(AdvertisementSaveRequest request) {
        try {
            RentalAd rentalAd = RentalAdConverter.toRentalInit(request);
            advertisementRepository.save(rentalAd);
            logProducer.sendToLog(prepareLogDto(OperationType.INSERT, ExceptionMessages.ADVERTISEMENT_CREATED, LogType.INFO));
            return RentalAdConverter.toResponse(rentalAd);
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.INSERT, ExceptionMessages.ADVERTISEMENT_SAVE_ERROR, LogType.ERROR));
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
                    logProducer.sendToLog(prepareLogDto(OperationType.UPDATE, ExceptionMessages.ADVERTISEMENT_TYPE_ERROR, LogType.ERROR));
                    throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_TYPE_ERROR);
                }
                RentalAd rentalAd = RentalAdConverter.toRentalAd(request);
                RentalAdConverter.updateAdFromRentalAd(updatedRentalAd, rentalAd);
                RentalAd saveAd = advertisementRepository.save(updatedRentalAd);
                logProducer.sendToLog(prepareLogDto(OperationType.UPDATE, ExceptionMessages.ADVERTISEMENT_UPDATED, LogType.INFO));
                return RentalAdConverter.toResponse(saveAd);
            }
            return null;
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.UPDATE, ExceptionMessages.ADVERTISEMENT_UPDATE_ERROR, LogType.ERROR));
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_UPDATE_ERROR);
        }
    }

    @Transactional
    public RentalAdResponse updateStatus(AdvertisementUpdateStatusRequest updateStatusRequest) {
        try {
            Advertisement foundedAdvertisement = advertisementRepository.findById(updateStatusRequest.getId())
                    .orElseThrow(() -> new AdvertisementException(ExceptionMessages.ADVERTISEMENT_NOT_FOUND));
            foundedAdvertisement.setAdvertisementStatus(updateStatusRequest.getStatus());
            Advertisement advertisement = advertisementRepository.save(foundedAdvertisement);
            logProducer.sendToLog(prepareLogDto(OperationType.UPDATE, ExceptionMessages.ADVERTISEMENT_UPDATED, LogType.INFO));
            return RentalAdConverter.toResponse((RentalAd) advertisement);
        } catch (AdvertisementException e) {
            logProducer.sendToLog(prepareLogDto(OperationType.UPDATE, ExceptionMessages.ADVERTISEMENT_UPDATE_ERROR, LogType.ERROR));
            throw e;
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.UPDATE, ExceptionMessages.ADVERTISEMENT_UPDATE_ERROR, LogType.ERROR));
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_UPDATE_ERROR);
        }
    }

    public List<RentalAdResponse> getAll() {
        try {
            List<RentalAd> rentalAds = advertisementRepository.findAll().stream()
                    .filter(ad -> ad instanceof RentalAd)
                    .map(ad -> (RentalAd) ad)
                    .toList();
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVED, LogType.INFO));
            return RentalAdConverter.toResponse(rentalAds);
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR, LogType.ERROR));
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR);
        }
    }

    public RentalAdResponse getById(Long id) {
        RentalAdResponse rentalAdResponse = advertisementRepository.findById(id)
                .filter(ad -> ad instanceof RentalAd)
                .map(ad -> (RentalAd) ad)
                .map(RentalAdConverter::toResponse)
                .orElseThrow(() -> new AdvertisementException(ExceptionMessages.ADVERTISEMENT_NOT_FOUND));
        logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVED, LogType.INFO));
        return rentalAdResponse;
    }

    public List<RentalAdResponse> getByUserId(Long userId) {
        List<RentalAdResponse> rentalAdResponses = getAll().stream()
                .filter(rentalAdResponse -> rentalAdResponse.getUserId().equals(userId))
                .toList();
        logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVED, LogType.INFO));
        return rentalAdResponses;
    }

    public List<RentalAdResponse> getByStatus(Long userId, AdvertisementStatus status) {
        try {
            List<RentalAd> foundedAdvertisements = advertisementRepository.findAll().stream()
                    .filter(ad -> ad instanceof RentalAd)
                    .filter(rentalAdResponse ->
                            rentalAdResponse.getAdvertisementStatus().equals(status) && rentalAdResponse.getUserId().equals(userId))
                    .map(ad -> (RentalAd) ad)
                    .toList();
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVED, LogType.INFO));
            return RentalAdConverter.toResponse(foundedAdvertisements);
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR, LogType.ERROR));
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR);
        }
    }

    private LogDto prepareLogDto(OperationType operationType, String message, LogType logType) {
        return LogDto.builder()
                .serviceName("advertisement-service(RentalAd)")
                .operationType(operationType)
                .logType(logType)
                .message(message)
                .timestamp(LocalDateTime.now())
                .exception("Advertisement Exception")
                .build();
    }
}
