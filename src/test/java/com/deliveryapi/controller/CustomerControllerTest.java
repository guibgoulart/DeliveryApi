package com.deliveryapi.controller;

import com.deliveryapi.application.CustomerService;
import com.deliveryapi.domain.customer.Customer;
import com.deliveryapi.domain.customer.CustomerMapper;
import com.deliveryapi.domain.customer.CustomerRequest;
import com.deliveryapi.domain.customer.CustomerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @Test
    public void testFindAllCustomers() throws Exception {
        // arrange
        Customer customer1 = Customer.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .build();
        Customer customer2 = Customer.builder()
                .id(2L)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .phone("0987654321")
                .build();
        List<Customer> customers = Arrays.asList(customer1, customer2);
        given(customerService.findAll()).willReturn(customers);

        // act
        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", Matchers.equalTo("John Doe")))
                .andExpect(jsonPath("$[0].email",  Matchers.equalTo("john.doe@example.com")))
                .andExpect(jsonPath("$[0].phone",  Matchers.equalTo("1234567890")))
                .andExpect(jsonPath("$[1].name",  Matchers.equalTo("Jane Doe")))
                .andExpect(jsonPath("$[1].email",  Matchers.equalTo("jane.doe@example.com")))
                .andExpect(jsonPath("$[1].phone",  Matchers.equalTo("0987654321")));

        // assert
        verify(customerService, times(1)).findAll();
    }

    @Test
    public void testFindCustomerById() throws Exception {
        // arrange
        Customer customer = Customer.builder()
                .id(1L)
                .name("John Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .build();
        given(customerService.findById(1L)).willReturn(customer);

        // act
        mockMvc.perform(get("/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.equalTo("John Doe")))
                .andExpect(jsonPath("$.email", Matchers.equalTo("john.doe@example.com")))
                .andExpect(jsonPath("$.phone", Matchers.equalTo("1234567890")));

        // assert
        verify(customerService, times(1)).findById(1L);
    }

    @Test
    public void testSaveCustomer() throws Exception {
        // arrange
        CustomerRequest customerRequest = CustomerRequest.builder()
                .name("John Doe")
                .email("john.doe@example.com")
                .phone("1234567890")
                .build();

        Customer customer = Customer.builder()
                .id(1L)
                .name(customerRequest.name())
                .email(customerRequest.email())
                .phone(customerRequest.phone())
                .build();

        given(customerService.save(any(Customer.class))).willReturn(customer);

        String requestJson = new ObjectMapper().writeValueAsString(customerRequest);

        // act
        mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", Matchers.equalTo(customer.getName())))
                .andExpect(jsonPath("$.email", Matchers.equalTo(customer.getEmail())))
                .andExpect(jsonPath("$.phone", Matchers.equalTo(customer.getPhone())));

        // assert
        verify(customerService, times(1)).save(any(Customer.class));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        // arrange
        Long customerId = 1L;
        String requestJson = "{\"name\":\"Jane Doe\",\"email\":\"jane.doe@example.com\",\"phone\":\"0987654321\"}";
        CustomerRequest customerRequest = CustomerRequest.builder()
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .phone("0987654321")
                .build();
        Customer updatedCustomer = Customer.builder()
                .id(customerId)
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .phone("0987654321")
                .build();
        given(customerService.update(customerId, customerMapper.customerRequestToCustomer(customerRequest)))
                .willReturn(updatedCustomer);

        // act
        mockMvc.perform(put("/customer/{customerId}", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", Matchers.equalTo("Jane Doe")))
                .andExpect(jsonPath("$.email", Matchers.equalTo("jane.doe@example.com")))
                .andExpect(jsonPath("$.phone", Matchers.equalTo("0987654321")));

        // assert
        verify(customerService, times(1))
                .update(customerId, customerMapper.customerRequestToCustomer(customerRequest));
    }

    @Test
    public void shouldDeleteCustomerById() throws Exception {
        // given
        Long customerId = 1L;

        // when
        mockMvc.perform(delete("/customer/{customerId}", customerId))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));

        // then
        verify(customerService, times(1)).deleteById(customerId);
    }


    @TestConfiguration
    static class CustomerControllerTestConfiguration {
        @Bean
        public CustomerMapper customerMapper() {
            return CustomerMapper.INSTANCE;
        }
    }
}
