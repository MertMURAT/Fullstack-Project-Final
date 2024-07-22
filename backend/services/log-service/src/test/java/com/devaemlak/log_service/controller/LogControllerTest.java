package com.devaemlak.log_service.controller;

import com.devaemlak.log_service.dto.request.LogSaveRequest;
import com.devaemlak.log_service.dto.request.LogUpdateRequest;
import com.devaemlak.log_service.dto.response.LogResponse;
import com.devaemlak.log_service.model.enums.LogType;
import com.devaemlak.log_service.model.enums.OperationType;
import com.devaemlak.log_service.service.LogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LogController.class)
class LogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LogService logService;

    @Test
    void save_successfully() throws Exception {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String body = objectMapper.writeValueAsString(prepareLogSaveRequest());

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/logs")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
        verify(logService, times(1)).save(Mockito.any(LogSaveRequest.class));
    }

    @Test
    void update_successfully() throws Exception {
        // given
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String body = objectMapper.writeValueAsString(prepareLogUpdateRequest());

        // when
        ResultActions resultActions = mockMvc.perform(put("/api/v1/logs/update")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
        verify(logService, times(1)).update(Mockito.any(LogUpdateRequest.class));
    }

    @Test
    void getAll_successfully() throws Exception {
        //given
        List<LogResponse> responses = Collections.singletonList(new LogResponse());
        when(logService.getAll()).thenReturn(responses);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/logs")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk());
        verify(logService, times(1)).getAll();
    }

    @Test
    void getById_successfully() throws Exception {
        //given
        String logId = "1";
        LogResponse response = new LogResponse();

        when(logService.getById(logId)).thenReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/logs/" + logId)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk());
        verify(logService, times(1)).getById(logId);
    }

    @Test
    void deleteById_successfully() throws Exception {
        //given
        String logId = "1";
        LogResponse response = new LogResponse();

        when(logService.deleteById(logId)).thenReturn(response);

        //when
        ResultActions resultActions = mockMvc.perform(delete("/api/v1/logs/" + logId)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk());
        verify(logService, times(1)).deleteById(logId);
    }

    private LogSaveRequest prepareLogSaveRequest() {
        return LogSaveRequest.builder()
                .serviceName("test-service")
                .message("test message")
                .operationType(OperationType.INSERT)
                .logType(LogType.INFO)
                .timestamp(LocalDateTime.now())
                .exception("test exception")
                .build();
    }

    private LogUpdateRequest prepareLogUpdateRequest() {
        return LogUpdateRequest.builder()
                .id("1")
                .serviceName("test-service")
                .message("updated message")
                .operationType(OperationType.DELETE)
                .logType(LogType.INFO)
                .timestamp(LocalDateTime.now())
                .exception("updated exception")
                .build();
    }
}