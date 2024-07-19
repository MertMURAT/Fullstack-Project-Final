package com.devaemlak.advertisement_service.service;

import com.devaemlak.advertisement_service.converter.AdvertisementConverter;
import com.devaemlak.advertisement_service.dto.request.AdvertisementSaveRequest;
import com.devaemlak.advertisement_service.dto.response.AdvertisementResponse;
import com.devaemlak.advertisement_service.exception.AdvertisementException;
import com.devaemlak.advertisement_service.exception.ExceptionMessages;
import com.devaemlak.advertisement_service.model.Advertisement;
import com.devaemlak.advertisement_service.model.enums.AdvertisementStatus;
import com.devaemlak.advertisement_service.model.enums.AdvertisementType;
import com.devaemlak.advertisement_service.model.enums.HousingType;
import com.devaemlak.advertisement_service.repository.AdvertisementRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    public Advertisement save(AdvertisementSaveRequest request){
        return advertisementRepository.save(AdvertisementConverter.toAdvertisement(request));
    }

    public List<Advertisement> getAll() {
        try {
            return advertisementRepository.findAll();
        } catch (Exception e) {
            log.error("Tüm ilanlar çekilirken hata oluştu: {}", e.getMessage());
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR);
        }
    }

    public List<Advertisement> getAllByActive() {
        try {
            return advertisementRepository.findAll().stream()
                    .filter(ad -> ad.getAdvertisementStatus().equals(AdvertisementStatus.ACTIVE))
                    .sorted((ad1, ad2) -> ad2.getId().compareTo(ad1.getId()))
                    .toList();
        } catch (Exception e) {
            log.error("İlanlar çekilirken hata oluştu: {}", e.getMessage());
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR);
        }
    }

    public List<Advertisement> search(AdvertisementType type, int area, int numberOfRooms, int floorNumber, String searchTerm, HousingType homeType) {
        try {
            return advertisementRepository.findAll().stream()
                    .filter(ad -> ad.getAdvertisementStatus().equals(AdvertisementStatus.ACTIVE))
                    .filter(ad -> ad.getAdvertisementType().equals(type))
                    .filter(ad -> ad.getArea() >= area)
                    .filter(ad -> ad.getNumberOfRooms() >= numberOfRooms)
                    .filter(ad -> ad.getFloorNumber() >= floorNumber)
                    .filter(ad -> ad.getAddress().toLowerCase().contains(searchTerm.toLowerCase()))
                    .filter(ad -> homeType == null || ad.getHousingType().equals(homeType))
                    .sorted((ad1, ad2) -> ad2.getId().compareTo(ad1.getId()))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("İlanlar çekilirken hata oluştu: {}", e.getMessage());
            throw new AdvertisementException(ExceptionMessages.ADVERTISEMENT_RETRIEVE_ERROR);
        }
    }
}
