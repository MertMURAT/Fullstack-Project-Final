package com.devaemlak.advertisement_service.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    @Id
    @GeneratedValue(generator= "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2" )
    @Column(name = "id")
    private String id;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "fileType")
    private String fileType;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "data")
    private byte[] data;

    @Column(name = "advertisement_id")
    private Long advertisementId;

}
