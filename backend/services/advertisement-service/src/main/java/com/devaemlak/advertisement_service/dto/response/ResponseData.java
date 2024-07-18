package com.devaemlak.advertisement_service.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseData {

    private String fileName;
    private String downloadURL;
    private String fileType;
    private long fileSize;
    private Long advertisement_id;

}
