package com.devaemlak.advertisement_service.service;

import com.devaemlak.advertisement_service.converter.AdvertisementConverter;
import com.devaemlak.advertisement_service.dto.request.AdvertisementSaveRequest;
import com.devaemlak.advertisement_service.exception.AdvertisementException;
import com.devaemlak.advertisement_service.exception.ExceptionMessages;
import com.devaemlak.advertisement_service.model.Advertisement;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.model.enums.AdvertisementType;
import com.devaemlak.advertisement_service.model.enums.HousingType;
import com.devaemlak.advertisement_service.producer.LogProducer;
import com.devaemlak.advertisement_service.producer.dto.LogDto;
import com.devaemlak.advertisement_service.producer.dto.enums.LogType;
import com.devaemlak.advertisement_service.producer.dto.enums.OperationType;
import com.devaemlak.advertisement_service.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final LogProducer logProducer;

    public Advertisement save(AdvertisementSaveRequest request) {
        Advertisement advertisement = advertisementRepository.save(AdvertisementConverter.toAdvertisement(request));
        logProducer.sendToLog(prepareLogDto(OperationType.INSERT, ExceptionMessages.ADVERTISEMENT_CREATED, LogType.INFO));
        return advertisement;
    }

    public List<Advertisement> getAll() {
        try {
            List<Advertisement> advertisements = advertisementRepository.findAll();
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVED, LogType.INFO));
            return advertisements;
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR, LogType.ERROR));
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR);
        }
    }

    public Advertisement getById(Long id) {
        try {
            Advertisement advertisement = advertisementRepository.findById(id)
                    .orElseThrow(() -> new AdvertisementException(ExceptionMessages.ADVERTISEMENT_NOT_FOUND));
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVED, LogType.INFO));
            return advertisement;
        } catch (AdvertisementException e) {
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR, LogType.ERROR));
            throw e;
        }
    }

    public List<Advertisement> getAllByActive() {
        try {
            List<Advertisement> advertisements = advertisementRepository.findAll().stream()
                    .filter(ad -> ad.getAdvertisementStatus().equals(AdvertisementStatus.ACTIVE))
                    .sorted((ad1, ad2) -> ad2.getId().compareTo(ad1.getId()))
                    .toList();
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVED, LogType.INFO));
            return advertisements;
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR, LogType.ERROR));
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR);
        }
    }

    public List<Advertisement> search(AdvertisementType type, int area, int numberOfRooms, int floorNumber, String searchTerm, HousingType homeType) {
        try {
            List<Advertisement> advertisements = advertisementRepository.findAll().stream()
                    .filter(ad -> ad.getAdvertisementStatus().equals(AdvertisementStatus.ACTIVE))
                    .filter(ad -> ad.getAdvertisementType().equals(type))
                    .filter(ad -> ad.getArea() >= area)
                    .filter(ad -> ad.getNumberOfRooms() >= numberOfRooms)
                    .filter(ad -> ad.getFloorNumber() >= floorNumber)
                    .filter(ad -> ad.getAddress().toLowerCase().contains(searchTerm.toLowerCase()))
                    .filter(ad -> homeType == null || ad.getHousingType().equals(homeType))
                    .sorted((ad1, ad2) -> ad2.getId().compareTo(ad1.getId()))
                    .toList();
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVED, LogType.INFO));
            return advertisements;
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR, LogType.ERROR));
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR);
        }
    }

    private LogDto prepareLogDto(OperationType operationType, String message, LogType logType) {
        return LogDto.builder()
                .serviceName("advertisement-service(advertisement)")
                .operationType(operationType)
                .logType(logType)
                .message(message)
                .timestamp(LocalDateTime.now())
                .exception("Advertisement Exception")
                .build();
    }
}
