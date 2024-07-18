package com.devaemlak.advertisement_service.controller;

import com.devaemlak.advertisement_service.dto.response.ResponseData;
import com.devaemlak.advertisement_service.model.Attachment;
import com.devaemlak.advertisement_service.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/attachments")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping("/upload/{id}")
    public ResponseData uploadFile(@PathVariable("id") Long advertisementId,
                                   @RequestParam("file") MultipartFile file) {
        Attachment attachment = null;
        String downloadURL = "";
        attachment = attachmentService.save(advertisementId, file);
        downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(attachment.getId())
                .toUriString();

        return ResponseData.builder()
                .advertisement_id(advertisementId)
                .fileName(attachment.getFileName())
                .downloadURL(downloadURL)
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .build();
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        Attachment attachment = null;
        attachment = attachmentService.getAttachment(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName() + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }
}
