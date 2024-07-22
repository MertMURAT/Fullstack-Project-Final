package com.devaemlak.advertisement_service.controller;

import com.devaemlak.advertisement_service.config.TestJpaConfig;
import com.devaemlak.advertisement_service.dto.request.AdvertisementSaveRequest;
import com.devaemlak.advertisement_service.dto.request.AdvertisementSearchRequest;
import com.devaemlak.advertisement_service.model.Advertisement;
import com.devaemlak.advertisement_service.service.AdvertisementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdvertisementController.class)
@Import(TestJpaConfig.class)
class AdvertisementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdvertisementService advertisementService;

    @Test
    void save_successfully() throws Exception {
        //given
        AdvertisementSaveRequest request = new AdvertisementSaveRequest();
        Advertisement advertisement = new Advertisement();
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(request);

        Mockito.when(advertisementService.save(Mockito.any(AdvertisementSaveRequest.class))).thenReturn(advertisement);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/advertisements")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isCreated());
        verify(advertisementService, times(1)).save(Mockito.any(AdvertisementSaveRequest.class));
    }

    @Test
    void getAll_successfully() throws Exception {
        //given
        List<Advertisement> advertisements = List.of(new Advertisement());
        Mockito.when(advertisementService.getAll()).thenReturn(advertisements);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/advertisements"));

        //then
        resultActions.andExpect(status().isOk());
        verify(advertisementService, times(1)).getAll();
    }

    @Test
    void getById_successfully() throws Exception {
        //given
        Long id = 1L;
        Advertisement advertisement = new Advertisement();
        Mockito.when(advertisementService.getById(id)).thenReturn(advertisement);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/advertisements/{id}", id));

        //then
        resultActions.andExpect(status().isOk());
        verify(advertisementService, times(1)).getById(id);
    }

    @Test
    void getLatestListing_successfully() throws Exception {
        //given
        List<Advertisement> advertisements = List.of(new Advertisement());
        Mockito.when(advertisementService.getAllByActive()).thenReturn(advertisements);

        //when
        ResultActions resultActions = mockMvc.perform(get("/api/v1/advertisements/latest"));

        //then
        resultActions.andExpect(status().isOk());
        verify(advertisementService, times(1)).getAllByActive();
    }

    @Test
    void getAllBySearchParams_successfully() throws Exception {
        //given
        AdvertisementSearchRequest request = new AdvertisementSearchRequest();
        List<Advertisement> advertisements = List.of(new Advertisement());
        ObjectMapper objectMapper = new ObjectMapper();
        String body = objectMapper.writeValueAsString(request);

        Mockito.when(advertisementService.getAllBySearchParams(Mockito.any(AdvertisementSearchRequest.class))).thenReturn(advertisements);

        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/advertisements/search")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk());
        verify(advertisementService, times(1)).getAllBySearchParams(Mockito.any(AdvertisementSearchRequest.class));
    }

    @Test
    void deleteById_successfully() throws Exception {
        //given
        Long id = 1L;

        //when
        ResultActions resultActions = mockMvc.perform(delete("/api/v1/advertisements/{id}", id));

        //then
        resultActions.andExpect(status().isOk());
        verify(advertisementService, times(1)).deleteById(id);
    }
}
