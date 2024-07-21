package com.devaemlak.advertisement_service.service;

import com.devaemlak.advertisement_service.exception.AdvertisementException;
import com.devaemlak.advertisement_service.exception.ExceptionMessages;
import com.devaemlak.advertisement_service.model.Advertisement;
import com.devaemlak.advertisement_service.model.Attachment;
import com.devaemlak.advertisement_service.producer.LogProducer;
import com.devaemlak.advertisement_service.producer.dto.LogDto;
import com.devaemlak.advertisement_service.producer.dto.enums.LogType;
import com.devaemlak.advertisement_service.producer.dto.enums.OperationType;
import com.devaemlak.advertisement_service.repository.AdvertisementRepository;
import com.devaemlak.advertisement_service.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AdvertisementRepository advertisementRepository;
    private final LogProducer logProducer;

    public List<Attachment> save(Long advertisementId, MultipartFile[] files) {
        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new AdvertisementException(ExceptionMessages.ADVERTISEMENT_NOT_FOUND));

        List<Attachment> attachments = new ArrayList<>();

        for (MultipartFile file : files) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            try {
                if (fileName.contains("..")) {
                    logProducer.sendToLog(prepareLogDto(OperationType.INSERT, ExceptionMessages.FILE_PATH_ERROR, LogType.ERROR));
                    throw new AdvertisementException(ExceptionMessages.FILE_PATH_ERROR + " Dosya: " + fileName);
                }

                Attachment attachment = Attachment.builder()
                        .fileName(fileName)
                        .fileType(file.getContentType())
                        .data(file.getBytes())
                        .advertisement(advertisement)
                        .build();

                attachments.add(attachment);
            } catch (Exception e) {
                logProducer.sendToLog(prepareLogDto(OperationType.INSERT, ExceptionMessages.FILE_ATTACHMENT_ERROR, LogType.ERROR));
                throw new AdvertisementException(ExceptionMessages.FILE_ATTACHMENT_ERROR + " Dosya: " + fileName);
            }
        }

        List<Attachment> savedAttachments = attachmentRepository.saveAll(attachments);
//        advertisement.setAttachments(savedAttachments);
        advertisementRepository.save(advertisement);

        logProducer.sendToLog(prepareLogDto(OperationType.INSERT, ExceptionMessages.FILE_ATTACHMENT_SAVED, LogType.INFO));
        return savedAttachments;
    }

    public Attachment getAttachment(String fileId) {
        Attachment attachment = attachmentRepository
                .findById(fileId)
                .orElseThrow(() -> new AdvertisementException(ExceptionMessages.FILE_NOT_FOUND));
        logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.FILE_ATTACHMENT_RETRIEVED, LogType.INFO));
        return attachment;
    }

    @Transactional(readOnly = true)
    public List<Attachment> getByAdvertisementId(Long advertisementId) {
        List<Attachment> attachments = attachmentRepository.findByAdvertisementId(advertisementId);
        logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.FILE_ATTACHMENT_RETRIEVED, LogType.INFO));
        return attachments;
    }

    @Transactional(readOnly = true)
    public List<Attachment> getAllAttachments() {
        try {
            List<Attachment> attachments = attachmentRepository.findAll();
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.FILE_ATTACHMENT_RETRIEVED, LogType.INFO));
            return attachments;
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.FILE_ATTACHMENT_RETRIEVED, LogType.ERROR));
            throw new AdvertisementException(ExceptionMessages.FILE_ATTACHMENT_RETRIEVE_ERROR);
        }
    }

    public Resource downloadFile(String fileId) {
        Attachment attachment = getAttachment(fileId);
        return new ByteArrayResource(attachment.getData());
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
