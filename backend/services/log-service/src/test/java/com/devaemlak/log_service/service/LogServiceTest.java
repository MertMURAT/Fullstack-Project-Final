package com.devaemlak.log_service.service;

import com.devaemlak.log_service.converter.LogConverter;
import com.devaemlak.log_service.dto.request.LogSaveRequest;
import com.devaemlak.log_service.dto.request.LogUpdateRequest;
import com.devaemlak.log_service.dto.response.LogResponse;
import com.devaemlak.log_service.exception.ExceptionMessages;
import com.devaemlak.log_service.exception.LogException;
import com.devaemlak.log_service.model.Log;
import com.devaemlak.log_service.repository.LogRepository;
import org.instancio.Instancio;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogServiceTest {

    @InjectMocks
    private LogService logService;

    @Mock
    private LogRepository logRepository;

    @Test
    void save_successfully() {
        //given
        LogSaveRequest request = Instancio.create(LogSaveRequest.class);
        Log log = LogConverter.toLog(request);

        when(logRepository.save(any(Log.class))).thenReturn(log);

        //when
        LogResponse logResponse = logService.save(request);

        //then
        verify(logRepository, times(1)).save(any(Log.class));
        assertThat(logResponse).isNotNull();
    }

    @Test
    void update_successfully() {
        //given
        LogUpdateRequest request = Instancio.create(LogUpdateRequest.class);
        Log log = Instancio.create(Log.class);

        when(logRepository.findById(request.getId())).thenReturn(Optional.of(log));
        when(logRepository.save(any(Log.class))).thenReturn(log);

        //when
        LogResponse logResponse = logService.update(request);

        //then
        verify(logRepository, times(1)).findById(request.getId());
        verify(logRepository, times(1)).save(any(Log.class));
        assertThat(logResponse).isNotNull();
    }

    @Test
    void shouldReturnNull_whenLogNotFoundForUpdate() {
        //given
        LogUpdateRequest request = Instancio.create(LogUpdateRequest.class);

        when(logRepository.findById(request.getId())).thenReturn(Optional.empty());

        //when
        LogResponse logResponse = logService.update(request);

        //then
        verify(logRepository, times(1)).findById(request.getId());
        verify(logRepository, times(0)).save(any(Log.class));
        assertThat(logResponse).isNull();
    }

    @Test
    void getAll_successfully() {
        //given
        Log log = Instancio.create(Log.class);
        when(logRepository.findAll()).thenReturn(Collections.singletonList(log));

        //when
        List<LogResponse> logResponses = logService.getAll();

        //then
        assertThat(logResponses).isNotEmpty();
        verify(logRepository, times(1)).findAll();
    }

    @Test
    void getById_successfully() {
        //given
        String logId = "1";
        Log log = Instancio.create(Log.class);

        when(logRepository.findById(logId)).thenReturn(Optional.of(log));

        //when
        LogResponse logResponse = logService.getById(logId);

        //then
        assertThat(logResponse).isNotNull();
        verify(logRepository, times(1)).findById(logId);
    }

    @Test
    void shouldReturnNull_whenLogNotFoundForGetById() {
        //given
        String logId = "1";

        when(logRepository.findById(logId)).thenReturn(Optional.empty());

        //when
        LogResponse logResponse = logService.getById(logId);

        //then
        verify(logRepository, times(1)).findById(logId);
        assertThat(logResponse).isNull();
    }

    @Test
    void deleteById_successfully() {
        //given
        String logId = "1";
        Log log = Instancio.create(Log.class);
        log.setId(logId);

        when(logRepository.findById(logId)).thenReturn(Optional.of(log));

        //when
        logService.deleteById(logId);

        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(logRepository, times(1)).findById(captor.capture());
        assertEquals(logId, captor.getValue());

        verify(logRepository, times(1)).deleteById(captor.capture());
        assertEquals(logId, captor.getValue());
    }

    @Test
    void shouldThrowException_whenLogNotFound() {
        //given
        String logId = "1";

        when(logRepository.findById(logId)).thenReturn(Optional.empty());

        //when
        LogException exception = Assertions.assertThrows(LogException.class, () -> {
            logService.deleteById(logId);
        });

        //then
        assertEquals(ExceptionMessages.LOG_NOT_FOUND, exception.getMessage());
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(logRepository, times(1)).findById(captor.capture());
        assertEquals(logId, captor.getValue());

        verify(logRepository, never()).deleteById(anyString());
    }
}