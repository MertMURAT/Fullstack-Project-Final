package com.devaemlak.order_service.service;

import com.devaemlak.order_service.converter.AdPackageConverter;
import com.devaemlak.order_service.converter.OrderConverter;
import com.devaemlak.order_service.dto.response.AdPackageResponse;
import com.devaemlak.order_service.exception.ExceptionMessages;
import com.devaemlak.order_service.exception.OrderException;
import com.devaemlak.order_service.model.AdPackage;
import com.devaemlak.order_service.model.Order;
import com.devaemlak.order_service.producer.LogProducer;
import com.devaemlak.order_service.producer.dto.LogDto;
import com.devaemlak.order_service.producer.dto.enums.LogType;
import com.devaemlak.order_service.producer.dto.enums.OperationType;
import com.devaemlak.order_service.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PackageService {

    private final PackageRepository packageRepository;
    private final LogProducer logProducer;
    private final TaskScheduler taskScheduler;

    public void save() {
        try {
            AdPackage adPackage = AdPackage.builder()
                    .quantity(10)
                    .createdDate(LocalDateTime.now())
                    .expiryDate(LocalDateTime.now().plusDays(30))
                    .build();
            AdPackage savedAdPackage = packageRepository.save(adPackage);
            logProducer.sendToLog(prepareLogDto(OperationType.INSERT, ExceptionMessages.AD_PACKAGE_SAVED, LogType.INFO));
            scheduleOrderDeletion(savedAdPackage);
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.INSERT, ExceptionMessages.AD_PACKAGE_SAVE_ERROR, LogType.ERROR));
            throw new OrderException(ExceptionMessages.AD_PACKAGE_SAVE_ERROR);
        }
    }

    public void decreaseByOne() {
        Optional<AdPackage> foundedAdPackage = packageRepository.findTopByOrderByIdAsc();
        if (foundedAdPackage.isPresent()) {
            AdPackage adPackage = foundedAdPackage.get();
            if (adPackage.getQuantity() > 0) {
                adPackage.setQuantity(adPackage.getQuantity() - 1);
                packageRepository.save(adPackage);
                logProducer.sendToLog(prepareLogDto(OperationType.UPDATE, ExceptionMessages.AD_PACKAGE_UPDATED, LogType.INFO));
            } else {
                packageRepository.delete(adPackage);
            }
        } else {
            throw new OrderException(ExceptionMessages.AD_PACKAGE_UPDATE_ERROR);
        }
    }

    private void scheduleOrderDeletion(AdPackage adPackage) {
        Runnable task = () -> {
            try {
                packageRepository.delete(adPackage);
                logProducer.sendToLog(prepareLogDto(OperationType.DELETE, ExceptionMessages.AD_PACKAGE_DELETED, LogType.INFO));
            } catch (Exception e) {
                logProducer.sendToLog(prepareLogDto(OperationType.DELETE, ExceptionMessages.AD_PACKAGE_DELETE_ERROR, LogType.ERROR));
                throw new OrderException(ExceptionMessages.AD_PACKAGE_DELETE_ERROR);
            }
        };
        Instant deleteTimeInstant = adPackage.getCreatedDate().plusSeconds(50000).atZone(ZoneId.systemDefault()).toInstant();
        taskScheduler.schedule(task, deleteTimeInstant);
    }

    public List<AdPackageResponse> getAll() {
        List<AdPackage> adPackages = packageRepository.findAll();
        logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.AD_PACKAGE_RETRIEVED, LogType.INFO));
        return AdPackageConverter.toResponse(adPackages);
    }

    public Integer getTotalQuantity() {
        List<AdPackage> adPackages = packageRepository.findAll();
        int totalQuantity = adPackages.stream()
                .mapToInt(AdPackage::getQuantity)
                .sum();
        logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.AD_PACKAGE_RETRIEVED, LogType.INFO));
        return totalQuantity;
    }


    private LogDto prepareLogDto(OperationType operationType, String message, LogType logType) {
        return LogDto.builder()
                .serviceName("order-service(ad-packages)")
                .operationType(operationType)
                .logType(logType)
                .message(message)
                .timestamp(LocalDateTime.now())
                .exception("Package Exception")
                .build();
    }

}
