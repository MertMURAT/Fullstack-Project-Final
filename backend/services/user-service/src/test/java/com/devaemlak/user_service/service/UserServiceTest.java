package com.devaemlak.user_service.service;

import com.devaemlak.user_service.converter.UserConverter;
import com.devaemlak.user_service.dto.request.UserSaveRequest;
import com.devaemlak.user_service.dto.response.UserResponse;
import com.devaemlak.user_service.exception.ExceptionMessages;
import com.devaemlak.user_service.exception.UserException;
import com.devaemlak.user_service.model.User;
import com.devaemlak.user_service.producer.LogProducer;
import com.devaemlak.user_service.producer.dto.LogDto;
import com.devaemlak.user_service.repository.UserRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LogProducer logProducer;

    @Test
    void save_successfully() {
        //given
        UserSaveRequest request = Instancio.create(UserSaveRequest.class);
        User user = UserConverter.toUser(request);

        when(userRepository.save(any(User.class))).thenReturn(user);

        //when
        userService.save(request);

        //then
        verify(userRepository, times(1)).save(any(User.class));
        verify(logProducer, times(1)).sendToLog(any(LogDto.class));
    }

    @Test
    void shouldThrowException_whenUserNotFound() {
        //given
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        //when
        UserException exception = Assertions.assertThrows(UserException.class, () -> {
            userService.getById(userId);
        });

        //then
        assertThat(exception.getMessage()).isEqualTo(ExceptionMessages.USER_NOT_FOUND);
    }

    @Test
    void getAll_successfully() {
        //given
        User user = Instancio.create(User.class);
        when(userRepository.findAll()).thenReturn(Collections.singletonList(user));

        //when
        List<UserResponse> userResponses = userService.getAll();

        //then
        assertThat(userResponses).isNotEmpty();
        verify(userRepository, times(1)).findAll();
        verify(logProducer, times(1)).sendToLog(any(LogDto.class));
    }

    @Test
    void getById_successfully() {
        //given
        Long userId = 1L;
        User user = Instancio.create(User.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        //when
        UserResponse userResponse = userService.getById(userId);

        //then
        assertThat(userResponse).isNotNull();
        verify(userRepository, times(1)).findById(userId);
        verify(logProducer, times(1)).sendToLog(any(LogDto.class));
    }
}