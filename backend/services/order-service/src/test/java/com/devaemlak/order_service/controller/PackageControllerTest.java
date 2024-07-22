package com.devaemlak.order_service.controller;

import com.devaemlak.order_service.dto.response.AdPackageResponse;
import com.devaemlak.order_service.dto.response.GenericResponse;
import com.devaemlak.order_service.service.PackageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PackageController.class)
class PackageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PackageService packageService;

    @Test
    void decreaseByOne_successfully() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(put("/api/v1/ad-packages")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk());
        verify(packageService, times(1)).decreaseByOne();
    }

    @Test
    void getAll_successfully() throws Exception {
        // given
        AdPackageResponse adPackageResponse = new AdPackageResponse();
        List<AdPackageResponse> adPackageResponses = Collections.singletonList(adPackageResponse);
        GenericResponse<List<AdPackageResponse>> response = GenericResponse.success(adPackageResponses);

        when(packageService.getAll()).thenReturn(adPackageResponses);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/ad-packages")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
        verify(packageService, times(1)).getAll();
    }

    @Test
    void getTotalQuantity_successfully() throws Exception {
        // given
        int totalQuantity = 100;
        GenericResponse<Integer> response = GenericResponse.success(totalQuantity);

        when(packageService.getTotalQuantity()).thenReturn(totalQuantity);

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/ad-packages/total-quantity")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(response)));
        verify(packageService, times(1)).getTotalQuantity();
    }
}