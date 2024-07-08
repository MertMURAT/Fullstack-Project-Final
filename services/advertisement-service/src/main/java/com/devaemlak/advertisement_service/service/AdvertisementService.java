package com.devaemlak.advertisement_service.service;

import com.devaemlak.advertisement_service.converter.AdvertisementConverter;
import com.devaemlak.advertisement_service.dto.request.AdvertisementUpdateStatusRequest;
import com.devaemlak.advertisement_service.dto.request.SaleAdSaveRequest;
import com.devaemlak.advertisement_service.dto.response.AdvertisementResponse;
import com.devaemlak.advertisement_service.exception.AdvertisementException;
import com.devaemlak.advertisement_service.exception.ExceptionMessages;
import com.devaemlak.advertisement_service.model.Advertisement;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    public List<Advertisement> getAll() {
        try {
            return advertisementRepository.findAll();
        } catch (Exception e) {
            log.error("Tüm ilanlar çekilirken hata oluştu: {}", e.getMessage());
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR);
        }
    }
}
