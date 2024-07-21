package com.devaemlak.advertisement_service.controller;

import com.devaemlak.advertisement_service.dto.response.GenericResponse;
import com.devaemlak.advertisement_service.dto.response.ResponseData;
import com.devaemlak.advertisement_service.model.Attachment;
import com.devaemlak.advertisement_service.service.AttachmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/attachments")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping("/upload/{id}")
    public ResponseEntity<List<ResponseData>> uploadFiles(@PathVariable("id") Long advertisementId,
                                                          @RequestParam("files") MultipartFile[] files) {
        List<Attachment> attachments = attachmentService.save(advertisementId, files);
        List<ResponseData> responseDataList = new ArrayList<>();

        for (Attachment attachment : attachments) {
            String downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/v1/attachments/download/")
                    .path(attachment.getId())
                    .toUriString();

            ResponseData responseData = ResponseData.builder()
                    .id(attachment.getId())
                    .advertisement_id(advertisementId)
                    .fileName(attachment.getFileName())
                    .downloadURL(downloadURL)
                    .fileType(attachment.getFileType())
                    .fileSize(attachment.getData().length)
                    .build();

            responseDataList.add(responseData);
        }

        return ResponseEntity.ok(responseDataList);
    }

//    @GetMapping("/advertisement/{id}")
//    public GenericResponse<List<Attachment>> getAttachmentsByAdvertisementId(@PathVariable Long id) {
//        return GenericResponse.success(attachmentService.getByAdvertisementId(id));
//    }
//
//    @GetMapping
//    public GenericResponse<List<Attachment>> getAllAttachments() {
//        return GenericResponse.success(attachmentService.getAllAttachments());
//    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        Resource resource = attachmentService.downloadFile(fileId);
        Attachment attachment = attachmentService.getAttachment(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName() + "\"")
                .body(resource);
    }
}
