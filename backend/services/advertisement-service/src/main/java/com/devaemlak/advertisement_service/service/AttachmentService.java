package com.devaemlak.advertisement_service.service;

import com.devaemlak.advertisement_service.exception.AdvertisementException;
import com.devaemlak.advertisement_service.exception.ExceptionMessages;
import com.devaemlak.advertisement_service.model.Attachment;
import com.devaemlak.advertisement_service.producer.LogProducer;
import com.devaemlak.advertisement_service.producer.dto.LogDto;
import com.devaemlak.advertisement_service.producer.dto.enums.LogType;
import com.devaemlak.advertisement_service.producer.dto.enums.OperationType;
import com.devaemlak.advertisement_service.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final LogProducer logProducer;

    public Attachment save(Long advertisementId, MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (fileName.contains("..")) {
                logProducer.sendToLog(prepareLogDto(OperationType.INSERT, ExceptionMessages.FILE_PATH_ERROR, LogType.ERROR));
                throw new AdvertisementException(ExceptionMessages.FILE_PATH_ERROR + "Dosya : " + fileName);
            }

            Attachment attachment = Attachment.builder()
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .data(file.getBytes())
                    .advertisementId(advertisementId)
                    .build();

            Attachment savedAttachment = attachmentRepository.save(attachment);
            logProducer.sendToLog(prepareLogDto(OperationType.INSERT, ExceptionMessages.FILE_ATTACHMENT_SAVED, LogType.INFO));
            return savedAttachment;

        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.INSERT, ExceptionMessages.FILE_ATTACHMENT_ERROR, LogType.ERROR));
            throw new AdvertisementException(ExceptionMessages.FILE_PATH_ERROR + "Dosya : " + fileName);
        }
    }

    public Attachment getAttachment(String fileId) {
        Attachment attachment = attachmentRepository
                .findById(fileId)
                .orElseThrow(() -> new AdvertisementException(ExceptionMessages.FILE_NOT_FOUND));
        logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.FILE_ATTACHMENT_RETRIEVED, LogType.INFO));
        return attachment;
    }

    private LogDto prepareLogDto(OperationType operationType, String message, LogType logType) {
        return LogDto.builder()
                .serviceName("advertisement-service(attachment)")
                .operationType(operationType)
                .logType(logType)
                .message(message)
                .timestamp(LocalDateTime.now())
                .exception("Attachment Exception")
                .build();
    }
}
