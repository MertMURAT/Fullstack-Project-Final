package com.devaemlak.order_service.service;

import com.devaemlak.order_service.dto.response.AdPackageResponse;
import com.devaemlak.order_service.exception.ExceptionMessages;
import com.devaemlak.order_service.exception.OrderException;
import com.devaemlak.order_service.model.AdPackage;
import com.devaemlak.order_service.producer.LogProducer;
import com.devaemlak.order_service.producer.dto.LogDto;
import com.devaemlak.order_service.producer.dto.enums.LogType;
import com.devaemlak.order_service.producer.dto.enums.OperationType;
import com.devaemlak.order_service.repository.PackageRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.TaskScheduler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackageServiceTest {

    @InjectMocks
    private PackageService packageService;

    @Mock
    private PackageRepository packageRepository;

    @Mock
    private LogProducer logProducer;

    @Mock
    private TaskScheduler taskScheduler;

    @Test
    void save_successfully() {
        //given
        AdPackage adPackage = Instancio.create(AdPackage.class);

        when(packageRepository.save(any(AdPackage.class))).thenReturn(adPackage);

        //when
        packageService.save();

        //then
        verify(packageRepository, times(1)).save(any(AdPackage.class));
        verify(logProducer, times(1)).sendToLog(any(LogDto.class));
    }

    @Test
    void shouldThrowException_whenSaveFails() {
        //given
        when(packageRepository.save(any(AdPackage.class))).thenThrow(new RuntimeException());

        //when
        OrderException exception = Assertions.assertThrows(OrderException.class, () -> {
            packageService.save();
        });

        //then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.AD_PACKAGE_SAVE_ERROR);
        verify(logProducer, times(1)).sendToLog(any(LogDto.class));
    }

    @Test
    void decreaseByOne_successfully() {
        //given
        AdPackage adPackage = Instancio.create(AdPackage.class);
        when(packageRepository.findTopByOrderByIdAsc()).thenReturn(Optional.of(adPackage));

        //when
        packageService.decreaseByOne();

        //then
        verify(packageRepository, times(1)).save(any(AdPackage.class));
        verify(logProducer, times(1)).sendToLog(any(LogDto.class));
    }

    @Test
    void shouldThrowException_whenDecreaseByOneFails() {
        //given
        when(packageRepository.findTopByOrderByIdAsc()).thenReturn(Optional.empty());

        //when
        OrderException exception = Assertions.assertThrows(OrderException.class, () -> {
            packageService.decreaseByOne();
        });

        //then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.AD_PACKAGE_UPDATE_ERROR);
//        verify(logProducer, times(1)).sendToLog(any(LogDto.class));
    }

    @Test
    void getAll_successfully() {
        //given
        List<AdPackage> adPackages = List.of(Instancio.create(AdPackage.class));
        when(packageRepository.findAll()).thenReturn(adPackages);

        //when
        List<AdPackageResponse> adPackageResponses = packageService.getAll();

        //then
        assertThat(adPackageResponses).isNotEmpty();
        verify(packageRepository, times(1)).findAll();
        verify(logProducer, times(1)).sendToLog(any(LogDto.class));
    }

    @Test
    void shouldThrowException_whenGetAllFails() {
        //given
        when(packageRepository.findAll()).thenThrow(new OrderException(ExceptionMessages.AD_PACKAGE_RETRIEVE_ERROR));

        //when
        OrderException exception = Assertions.assertThrows(OrderException.class, () -> {
            packageService.getAll();
        });

        //then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.AD_PACKAGE_RETRIEVE_ERROR);
    }

    @Test
    void getTotalQuantity_successfully() {
        //given
        List<AdPackage> adPackages = List.of(Instancio.create(AdPackage.class));
        when(packageRepository.findAll()).thenReturn(adPackages);

        //when
        Integer totalQuantity = packageService.getTotalQuantity();

        //then
        assertThat(totalQuantity).isNotNull();
        verify(packageRepository, times(1)).findAll();
        verify(logProducer, times(1)).sendToLog(any(LogDto.class));
    }
}