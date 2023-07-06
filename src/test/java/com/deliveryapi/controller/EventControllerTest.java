package com.deliveryapi.controller;

import com.deliveryapi.application.DeliveryRequestService;
import com.deliveryapi.application.EventRegisterService;
import com.deliveryapi.domain.delivery.Delivery;
import com.deliveryapi.domain.event.Event;
import com.deliveryapi.domain.event.EventMapper;
import com.deliveryapi.domain.event.EventResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryRequestService deliveryRequestService;

    @MockBean
    private EventRegisterService eventRegisterService;

    @MockBean
    private EventMapper mapper;

    private Delivery delivery;
    private EventResponse eventResponse;
    private Event event;

    @BeforeEach
    void setUp() {
        delivery = new Delivery();
        delivery.setId(1L);

        event = new Event();
        event.setDescription("Event Description");
        event.setDelivery(delivery);

        eventResponse = new EventResponse(1L, "Description", OffsetDateTime.now().withNano(0));

        when(mapper.eventToEventResponse(event)).thenReturn(eventResponse);
        when(mapper.eventListToEventResponseList(anyList())).thenReturn(Arrays.asList(eventResponse));
    }

    @Test
    void registerTest() throws Exception {
        when(eventRegisterService.register(anyLong(), anyString())).thenReturn(event);

        mockMvc.perform(post("/delivery/1/event")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"description\":\"Event Description\"}"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"description\":\"Event Description\",\"deliveryId\":1}"));

        verify(eventRegisterService, times(1)).register(anyLong(), anyString());
    }

    @Test
    void listTest() throws Exception {

        when(deliveryRequestService.findById(1L)).thenReturn(delivery);

        mockMvc.perform(get("/delivery/" + 1L + "/event")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].description").value("Description"));

        verify(deliveryRequestService, times(1)).findById(1L);
    }
}

