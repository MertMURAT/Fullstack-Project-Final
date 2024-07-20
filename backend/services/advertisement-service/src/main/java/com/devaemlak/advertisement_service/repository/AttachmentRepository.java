package com.devaemlak.advertisement_service.repository;

import com.devaemlak.advertisement_service.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, String> {
    List<Attachment> findByAdvertisementId(Long advertisementId);
}
