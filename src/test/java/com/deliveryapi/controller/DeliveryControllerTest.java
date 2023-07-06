package com.deliveryapi.controller;

import com.deliveryapi.application.DeliveryFinishService;
import com.deliveryapi.application.DeliveryRequestService;
import com.deliveryapi.domain.delivery.*;
import com.deliveryapi.domain.customer.Customer;
import com.deliveryapi.domain.customer.CustomerIdRequest;
import com.deliveryapi.domain.customer.CustomerResponse;
import com.deliveryapi.domain.recipient.Recipient;
import com.deliveryapi.domain.recipient.RecipientRequest;
import com.deliveryapi.domain.recipient.RecipientResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(DeliveryController.class)
public class DeliveryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeliveryRequestService deliveryRequestService;

    @MockBean
    private DeliveryFinishService deliveryFinishService;

    @Autowired
    private DeliveryMapper deliveryMapper;

    private DeliveryRequest deliveryRequest;
    private Delivery delivery;
    private DeliveryResponse deliveryResponse;

    @BeforeEach
    void setUp() {
        deliveryRequest = new DeliveryRequest(
                new CustomerIdRequest(1L),
                new RecipientRequest("RecipientName", "Address"),
                BigDecimal.valueOf(100.0)
        );

        Customer customer = new Customer();
        customer.setId(1L);
        customer.setName("Name");
        customer.setEmail("Email");
        customer.setPhone("Phone");

        Recipient recipient = new Recipient();
        recipient.setAddress("Address");
        recipient.setName("RecipientName");

        delivery = Delivery.builder()
                .id(1L)
                .customer(customer)
                .recipient(recipient)
                .cost(BigDecimal.valueOf(100.0))
                .status(DeliveryStatus.PENDING)
                .requestDate(OffsetDateTime.now().withNano(0)) // removes nanosecond precision
                .build();

        deliveryResponse = DeliveryResponse.builder()
                .id(1L)
                .customer(new CustomerResponse(1L, "Name", "Email", "Phone"))
                .recipient(new RecipientResponse("RecipientName", "Address"))
                .cost(BigDecimal.valueOf(100.0))
                .status(DeliveryStatus.PENDING)
                .requestDate(delivery.getRequestDate()) // use the same requestDate as delivery
                .build();
    }

    @Test
    void requestDelivery() throws Exception {
        given(deliveryRequestService.request(Mockito.any())).willReturn(delivery);

        mockMvc.perform(post("/delivery")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"customer\":{\"id\":1},\"recipient\":{\"name\":\"RecipientName\",\"address\":\"Address\"},\"cost\":100.0}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1,\"customer\":{\"id\":1,\"name\":\"Name\",\"email\":\"Email\",\"phone\":\"Phone\"},\"recipient\":{\"name\":\"RecipientName\",\"address\":\"Address\"},\"cost\":100.0,\"status\":\"PENDING\",\"requestDate\":\"" + deliveryResponse.requestDate().format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) + "\"}"));
    }

    @Test
    void findAll() throws Exception {
        given(deliveryRequestService.findAll()).willReturn(Collections.singletonList(delivery));

        mockMvc.perform(get("/delivery"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1,\"customer\":{\"id\":1,\"name\":\"Name\",\"email\":\"Email\",\"phone\":\"Phone\"},\"recipient\":{\"name\":\"RecipientName\",\"address\":\"Address\"},\"cost\":100.0,\"status\":\"PENDING\",\"requestDate\":\"" + deliveryResponse.requestDate() + "\"}]"));
    }

    @Test
    void findById() throws Exception {
        given(deliveryRequestService.findById(Mockito.anyLong())).willReturn(delivery);

        mockMvc.perform(get("/delivery/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"customer\":{\"id\":1,\"name\":\"Name\",\"email\":\"Email\",\"phone\":\"Phone\"},\"recipient\":{\"name\":\"RecipientName\",\"address\":\"Address\"},\"cost\":100.0,\"status\":\"PENDING\",\"requestDate\":\"" + deliveryResponse.requestDate() + "\"}"));
    }

    @Test
    void finishDelivery() throws Exception {
        Mockito.doNothing().when(deliveryFinishService).finish(Mockito.anyLong());

        mockMvc.perform(put("/delivery/1/finish"))
                .andExpect(status().isNoContent());
    }

    @TestConfiguration
    static class DeliveryControllerTestConfiguration {
        @Bean
        public DeliveryMapper deliveryMapper() {
            return DeliveryMapper.INSTANCE;
        }
    }
}
