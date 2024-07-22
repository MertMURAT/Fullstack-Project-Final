package com.devaemlak.user_service.controller;

import com.devaemlak.user_service.dto.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.devaemlak.user_service.dto.request.UserSaveRequest;
import com.devaemlak.user_service.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.devaemlak.user_service.dto.response.GenericResponse;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void save_successfully() throws Exception {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(prepareUserSaveRequest());

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/users")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isCreated());
        verify(userService, times(1)).save(Mockito.any(UserSaveRequest.class));
    }

    @Test
    void getById_successfully() throws Exception {
        //given
        Long userId = 1L;
        UserResponse userResponse = new UserResponse();
        userResponse.setId(userId);

        when(userService.getById(userId)).thenReturn(userResponse);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk());
        verify(userService, times(1)).getById(userId);
    }

    @Test
    void getAll_successfully() throws Exception {
        // given
        UserResponse userResponse = UserResponse.builder()
                .id(1L)
                .name("John")
                .surname("Doe")
                .email("john.doe@example.com")
                .password("password")
                .phoneNumber("1234567890")
                .isActive(true)
                .packageCredit(10)
                .build();

        List<UserResponse> userResponses = Collections.singletonList(userResponse);

        GenericResponse<List<UserResponse>> response = GenericResponse.success(userResponses);

        when(userService.getAll()).thenReturn(userResponses);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)));

        verify(userService, times(1)).getAll();
    }

    private UserSaveRequest prepareUserSaveRequest() {
        return UserSaveRequest.builder()
                .name("Mert")
                .surname("MURAT")
                .email("mertmurat@gmail.com")
                .password("password123")
                .phoneNumber("05331112233")
                .build();
    }
}