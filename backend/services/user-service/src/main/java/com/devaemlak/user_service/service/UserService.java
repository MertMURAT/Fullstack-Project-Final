package com.devaemlak.user_service.service;

import com.devaemlak.user_service.converter.UserConverter;
import com.devaemlak.user_service.dto.request.UserSaveRequest;
import com.devaemlak.user_service.dto.response.UserResponse;
import com.devaemlak.user_service.exception.ExceptionMessages;
import com.devaemlak.user_service.exception.UserException;
import com.devaemlak.user_service.producer.LogProducer;
import com.devaemlak.user_service.producer.dto.LogDto;
import com.devaemlak.user_service.producer.dto.enums.LogType;
import com.devaemlak.user_service.producer.dto.enums.OperationType;
import com.devaemlak.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LogProducer logProducer;

    @Transactional
    public void save(UserSaveRequest request){
        try {
            userRepository.save(UserConverter.toUser(request));
            logProducer.sendToLog(prepareLogDto(OperationType.INSERT, ExceptionMessages.USER_SAVED, LogType.INFO));
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.INSERT, ExceptionMessages.USER_SAVE_ERROR, LogType.ERROR));
            throw new UserException(ExceptionMessages.USER_SAVE_ERROR);
        }
    }

    public List<UserResponse> getAll(){
        try {
            List<UserResponse> userResponses = UserConverter.toResponse(userRepository.findAll());
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.USER_RETRIEVED, LogType.INFO));
            return userResponses;
        } catch (Exception e) {
            logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.USER_RETRIEVE_ERROR, LogType.ERROR));
            throw new UserException(ExceptionMessages.USER_RETRIEVE_ERROR);
        }
    }

    public UserResponse getById(Long id){
        UserResponse userResponse = userRepository.findById(id)
                .map(UserConverter::toResponse)
                .orElseThrow(() -> new UserException(ExceptionMessages.USER_NOT_FOUND));
        logProducer.sendToLog(prepareLogDto(OperationType.GET, ExceptionMessages.USER_FOUNDED, LogType.INFO));
        return userResponse;
    }

    private LogDto prepareLogDto(OperationType operationType, String message, LogType logType) {
        return LogDto.builder()
                .serviceName("user-service")
                .operationType(operationType)
                .logType(logType)
                .message(message)
                .timestamp(LocalDateTime.now())
                .exception("User Exception")
                .build();
    }
}
