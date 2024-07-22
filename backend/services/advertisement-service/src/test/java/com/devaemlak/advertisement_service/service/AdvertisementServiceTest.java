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
import com.devaemlak.advertisement_service.repository.AdvertisementRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.instancio.Select.field;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdvertisementServiceTest {

    @InjectMocks
    private AdvertisementService advertisementService;

    @Mock
    private AdvertisementRepository advertisementRepository;

    @Mock
    private LogProducer logProducer;

    @Mock
    private GeometryFactory geometryFactory;

    @Test
    void save_successfully() {
        //given
        AdvertisementSaveRequest request = Instancio.of(AdvertisementSaveRequest.class)
                .create();
        Advertisement advertisement = Instancio.of(Advertisement.class)
                .create();
        when(advertisementRepository.save(any(Advertisement.class))).thenReturn(advertisement);

        //when
        advertisementService.save(request);

        //then
        verify(advertisementRepository, times(1)).save(any(Advertisement.class));
        verify(logProducer, times(1)).sendToLog(any());
    }


    @Test
    void getAll_successfully() {
        //given
        List<Advertisement> advertisements = List.of(Instancio.create(Advertisement.class));
        when(advertisementRepository.findAll()).thenReturn(advertisements);

        //when
        List<Advertisement> result = advertisementService.getAll();

        //then
        assertThat(result).isNotEmpty();
        verify(advertisementRepository, times(1)).findAll();
    }

    @Test
    void getById_successfully() {
        //given
        Long id = 1L;
        Advertisement advertisement = Instancio.create(Advertisement.class);
        when(advertisementRepository.findById(id)).thenReturn(Optional.of(advertisement));

        //when
        Advertisement result = advertisementService.getById(id);

        //then
        assertThat(result).isNotNull();
        verify(advertisementRepository, times(1)).findById(id);
    }

    @Test
    void shouldThrowException_whenAdvertisementNotFound() {
        //given
        Long id = 1L;
        when(advertisementRepository.findById(id)).thenReturn(Optional.empty());

        //when
        AdvertisementException exception = Assertions.assertThrows(AdvertisementException.class, () -> {
            advertisementService.getById(id);
        });

        //then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.ADVERTISEMENT_NOT_FOUND);
        verify(logProducer, times(1)).sendToLog(any());
    }


    @Test
    void getAllByActive_successfully() {
        //given
        Advertisement activeAd = Instancio.of(Advertisement.class)
                .set(field(Advertisement::getAdvertisementStatus), AdvertisementStatus.ACTIVE)
                .create();
        Advertisement inactiveAd = Instancio.of(Advertisement.class)
                .set(field(Advertisement::getAdvertisementStatus), AdvertisementStatus.IN_REVIEW)
                .create();
        List<Advertisement> advertisements = List.of(activeAd, inactiveAd);

        when(advertisementRepository.findAll()).thenReturn(advertisements);

        //when
        List<Advertisement> result = advertisementService.getAllByActive();

        //then
        assertThat(result).isNotEmpty();
        assertThat(result).allMatch(ad -> ad.getAdvertisementStatus() == AdvertisementStatus.ACTIVE);
        verify(advertisementRepository, times(1)).findAll();
        verify(logProducer, times(1)).sendToLog(any(LogDto.class));
    }


    @Test
    void getAllBySearchParams_successfully() {
        //given
        AdvertisementSearchRequest request = Instancio.create(AdvertisementSearchRequest.class);
        List<Advertisement> advertisements = List.of(Instancio.create(Advertisement.class));
        Page<Advertisement> page = new PageImpl<>(advertisements);
        when(advertisementRepository.findAll(any(Specification.class), any(PageRequest.class))).thenReturn(page);

        //when
        List<Advertisement> result = advertisementService.getAllBySearchParams(request);

        //then
        assertThat(result).isNotEmpty();
        verify(advertisementRepository, times(1)).findAll(any(Specification.class), any(PageRequest.class));
        verify(logProducer, times(1)).sendToLog(any(LogDto.class));
    }

    @Test
    void deleteById_successfully() {
        //given
        Long id = 1L;
        Advertisement advertisement = Instancio.create(Advertisement.class);

        lenient().doAnswer((Answer<Optional<Advertisement>>) invocation -> {
            Long argument = invocation.getArgument(0, Long.class);
            return argument.equals(id) ? Optional.of(advertisement) : Optional.empty();
        }).when(advertisementRepository).findById(anyLong());

        // Ensure the advertisement exists
        lenient().when(advertisementRepository.existsById(id)).thenReturn(true);

        //when
        advertisementService.deleteById(id);

        //then
        verify(advertisementRepository, times(1)).deleteById(id);
        verify(logProducer, times(1)).sendToLog(any());
    }

    @Test
    void shouldThrowException_whenDeleteByIdFails() {
        // Given
        Long id = 1L;
        when(advertisementRepository.existsById(id)).thenReturn(false);

        // When & Then
        AdvertisementException exception = Assertions.assertThrows(AdvertisementException.class, () -> {
            advertisementService.deleteById(id);
        });

        // Assert
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.ADVERTISEMENT_NOT_FOUND);
        verify(logProducer, times(1)).sendToLog(any());
    }
}