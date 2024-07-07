package com.devaemlak.user_service.converter;

import com.devaemlak.user_service.dto.request.UserSaveRequest;
import com.devaemlak.user_service.dto.response.UserResponse;
import com.devaemlak.user_service.model.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConverter {

    public static User toUser(UserSaveRequest request){
        return User.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .isActive(true)
                .packageCredit(3)
                .build();
    }

    public static UserResponse toResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .isActive(user.getIsActive())
                .packageCredit(user.getPackageCredit())
                .build();
    }

    public static List<UserResponse> toResponse(List<User> users){
        return users.stream()
                .map(UserConverter::toResponse)
                .toList();
    }
}
