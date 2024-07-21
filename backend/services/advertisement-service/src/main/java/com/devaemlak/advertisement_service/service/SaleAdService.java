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
import com.devaemlak.advertisement_service.producer.LogProducer;
import com.devaemlak.advertisement_service.producer.dto.LogDto;
import com.devaemlak.advertisement_service.producer.dto.enums.LogType;
import com.devaemlak.advertisement_service.producer.dto.enums.OperationType;
import com.devaemlak.advertisement_service.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaleAdService {

    private final AdvertisementRepository advertisementRepository;
    private final GeometryFactory geometryFactory;
    private final LogProducer logProducer;

    @Transactional
    public SaleAdResponse save(AdvertisementSaveRequest request) {
        try {
            Point point = geometryFactory.createPoint(new Coordinate(request.getCoordinates().getLng(), request.getCoordinates().getLat()));
            SaleAd saleAd = SaleAdConverter.toSaleInit(request);
            saleAd.setCoordinates(point);
            advertisementRepository.save(saleAd);
            logProducer.sendToLog(prepareLogDto(OperationType.INSERT, ExceptionMessages.ADVERTISEMENT_CREATED, LogType.INFO));
            return SaleAdConverter.toResponse(saleAd);
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.INSERT, ExceptionMessages.ADVERTISEMENT_SAVE_ERROR, LogType.ERROR));
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
                    throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_TYPE_ERROR);
                }
                SaleAd saleAd = SaleAdConverter.toSaleAd(request);
                SaleAdConverter.updateAdFromSaleAd(updatedSaleAd, saleAd);
                SaleAd saveAd = advertisementRepository.save(updatedSaleAd);
                logProducer.sendToLog(prepareLogDto(OperationType.UPDATE, ExceptionMessages.ADVERTISEMENT_UPDATED, LogType.INFO));
                return SaleAdConverter.toResponse(saveAd);
            }
            return null;
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.UPDATE, ExceptionMessages.ADVERTISEMENT_UPDATED, LogType.INFO));
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
            logProducer.sendToLog(prepareLogDto(OperationType.UPDATE, ExceptionMessages.ADVERTISEMENT_UPDATED, LogType.INFO));
            return SaleAdConverter.toResponse((SaleAd) advertisement);
        } catch (AdvertisementException e) {
            throw e;
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.UPDATE, ExceptionMessages.ADVERTISEMENT_UPDATE_ERROR, LogType.ERROR));
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_UPDATE_ERROR);
        }
    }

    public List<SaleAdResponse> getAll() {
        try {
            List<SaleAd> saleAds = advertisementRepository.findAll().stream()
                    .filter(ad -> ad instanceof SaleAd)
                    .map(ad -> (SaleAd) ad)
                    .toList();
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVED, LogType.INFO));
            return SaleAdConverter.toResponse(saleAds);
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR, LogType.ERROR));
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR);
        }
    }

    public SaleAdResponse getById(Long id) {
        SaleAdResponse saleAdResponse = advertisementRepository.findById(id)
                .filter(ad -> ad instanceof SaleAd)
                .map(ad -> (SaleAd) ad)
                .map(SaleAdConverter::toResponse)
                .orElseThrow(() -> new AdvertisementException(ExceptionMessages.ADVERTISEMENT_NOT_FOUND));
        logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVED, LogType.INFO));
        return saleAdResponse;
    }

    public List<SaleAdResponse> getByUserId(Long userId) {
        List<SaleAdResponse> saleAdResponses = getAll().stream()
                .filter(saleAdResponse -> saleAdResponse.getUserId().equals(userId))
                .toList();
        logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVED, LogType.INFO));
        return saleAdResponses;
    }

    public List<SaleAdResponse> getByStatus(Long userId, AdvertisementStatus status) {
        try {
            List<SaleAd> foundedAdvertisements = advertisementRepository.findAll().stream()
                    .filter(ad -> ad instanceof SaleAd)
                    .filter(saleAdResponse ->
                            saleAdResponse.getAdvertisementStatus().equals(status) && saleAdResponse.getUserId().equals(userId))
                    .map(ad -> (SaleAd) ad)
                    .toList();
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVED, LogType.INFO));
            return SaleAdConverter.toResponse(foundedAdvertisements);
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR, LogType.ERROR));
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR);
        }
    }

    private LogDto prepareLogDto(OperationType operationType, String message, LogType logType) {
        return LogDto.builder()
                .serviceName("advertisement-service(SaleAd)")
                .operationType(operationType)
                .logType(logType)
                .message(message)
                .timestamp(LocalDateTime.now())
                .exception("Advertisement Exception")
                .build();
    }
}
