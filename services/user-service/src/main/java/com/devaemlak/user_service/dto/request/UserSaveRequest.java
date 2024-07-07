package com.devaemlak.user_service.dto.request;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveRequest {

    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;

}
