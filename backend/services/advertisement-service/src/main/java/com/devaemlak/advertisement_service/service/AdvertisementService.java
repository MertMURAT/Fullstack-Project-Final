package com.devaemlak.advertisement_service.service;

import com.devaemlak.advertisement_service.converter.AdvertisementConverter;
import com.devaemlak.advertisement_service.dto.request.AdvertisementSaveRequest;
import com.devaemlak.advertisement_service.dto.request.AdvertisementSearchRequest;
import com.devaemlak.advertisement_service.exception.AdvertisementException;
import com.devaemlak.advertisement_service.exception.ExceptionMessages;
import com.devaemlak.advertisement_service.model.Advertisement;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.producer.LogProducer;
import com.devaemlak.advertisement_service.producer.dto.LogDto;
import com.devaemlak.advertisement_service.producer.dto.enums.LogType;
import com.devaemlak.advertisement_service.producer.dto.enums.OperationType;
import com.devaemlak.advertisement_service.repository.AdvertisementRepository;
import com.devaemlak.advertisement_service.repository.specification.AdvertisementSpecification;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;
    private final GeometryFactory geometryFactory;
    private final LogProducer logProducer;

    public Advertisement save(AdvertisementSaveRequest request) {
        Point point = geometryFactory.createPoint(new Coordinate(request.getCoordinates().getLng(), request.getCoordinates().getLat()));
        Advertisement advertisement = AdvertisementConverter.toAdvertisement(request);
        advertisement.setCoordinates(point);
        advertisementRepository.save(advertisement);
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

    public List<Advertisement> getAllBySearchParams(AdvertisementSearchRequest request) {

        Specification<Advertisement> advertisementSpecification = AdvertisementSpecification.initAdvertisementSpecification(request);
        PageRequest pageRequest = PageRequest.of(request.getPage(), request.getSize());
        Page<Advertisement> advertisements = advertisementRepository.findAll(advertisementSpecification, pageRequest);
        logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.ADVERTISEMENT_RETRIEVED, LogType.INFO));
        return advertisements.stream().toList();
    }

    public void deleteById(Long id) {
        advertisementRepository.deleteById(id);
        logProducer.sendToLog(prepareLogDto(OperationType.DELETE, ExceptionMessages.ADVERTISEMENT_DELETED, LogType.INFO));
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
