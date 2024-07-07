package com.devaemlak.user_service.service;

import com.devaemlak.user_service.converter.UserConverter;
import com.devaemlak.user_service.dto.request.UserSaveRequest;
import com.devaemlak.user_service.dto.response.UserResponse;
import com.devaemlak.user_service.model.User;
import com.devaemlak.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void save(UserSaveRequest request){
        userRepository.save(UserConverter.toUser(request));
    }

    public List<UserResponse> getAll(){
        return UserConverter.toResponse(userRepository.findAll());
    }

    public UserResponse getById(Long id){
        return getAll().stream()
                .filter(userResponse -> userResponse.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
