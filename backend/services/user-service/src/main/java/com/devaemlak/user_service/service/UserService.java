package com.devaemlak.user_service.service;

import com.devaemlak.user_service.converter.UserConverter;
import com.devaemlak.user_service.dto.request.UserSaveRequest;
import com.devaemlak.user_service.dto.response.UserResponse;
import com.devaemlak.user_service.exception.ExceptionMessages;
import com.devaemlak.user_service.exception.UserException;
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
        try {
            userRepository.save(UserConverter.toUser(request));
        } catch (Exception e) {
            log.error("Kullanıcı kaydedilirken hata oluştu: {}", e.getMessage());
            throw new UserException(ExceptionMessages.USER_SAVE_ERROR);
        }
    }

    public List<UserResponse> getAll(){
        try {
            return UserConverter.toResponse(userRepository.findAll());
        } catch (Exception e) {
            log.error("Tüm kullanıcılar getirilirken hata oluştu: {}", e.getMessage());
            throw new UserException(ExceptionMessages.USER_RETRIEVE_ERROR);
        }
    }

    public UserResponse getById(Long id){
        return userRepository.findById(id)
                .map(UserConverter::toResponse)
                .orElseThrow(() -> new UserException(ExceptionMessages.USER_NOT_FOUND));
    }
}
