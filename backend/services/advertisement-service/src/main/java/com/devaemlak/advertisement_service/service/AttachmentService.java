package com.devaemlak.advertisement_service.service;

import com.devaemlak.advertisement_service.dto.response.ResponseData;
import com.devaemlak.advertisement_service.exception.AdvertisementException;
import com.devaemlak.advertisement_service.exception.ExceptionMessages;
import com.devaemlak.advertisement_service.model.Attachment;
import com.devaemlak.advertisement_service.repository.AttachmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    public Attachment save(Long advertisementId, MultipartFile file) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            if (fileName.contains("..")) {
                throw new AdvertisementException(ExceptionMessages.FILE_PATH_ERROR + "Dosya : " + fileName);
            }

            Attachment attachment = Attachment.builder()
                    .fileName(fileName)
                    .fileType(file.getContentType())
                    .data(file.getBytes())
                    .advertisementId(advertisementId)
                    .build();

            return attachmentRepository.save(attachment);

        } catch (Exception e) {
            throw new AdvertisementException(ExceptionMessages.FILE_PATH_ERROR + "Dosya : " + fileName);
        }
    }

    public Attachment getAttachment(String fileId) {
        return attachmentRepository
                .findById(fileId)
                .orElseThrow(() -> new AdvertisementException(ExceptionMessages.FILE_NOT_FOUND));
    }
}
