package com.deliveryapi.application;

import com.deliveryapi.domain.delivery.Delivery;
import com.deliveryapi.domain.delivery.DeliveryRepository;
import com.deliveryapi.domain.delivery.DeliveryStatus;
import com.deliveryapi.domain.customer.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DeliveryRequestServiceTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private DeliveryRequestService deliveryRequestService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRequest() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);

        Delivery delivery = new Delivery();
        delivery.setCustomer(customer);

        // When
        when(customerService.findById(anyLong())).thenReturn(customer);
        when(deliveryRepository.save(any(Delivery.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Delivery requestedDelivery = deliveryRequestService.request(delivery);

        // Then
        verify(customerService, times(1)).findById(anyLong());
        verify(deliveryRepository, times(1)).save(any(Delivery.class));
        assert(requestedDelivery.getStatus().equals(DeliveryStatus.PENDING));
        assert(requestedDelivery.getRequestDate() != null);
    }

    @Test
    public void testFindAll() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);

        Delivery delivery = new Delivery();
        delivery.setCustomer(customer);

        // When
        when(deliveryRepository.findAll()).thenReturn(Collections.singletonList(delivery));

        List<Delivery> deliveries = deliveryRequestService.findAll();

        // Then
        verify(deliveryRepository, times(1)).findAll();
        assert(deliveries.size() == 1);
    }

    @Test
    public void testFindById() {
        // Given
        Customer customer = new Customer();
        customer.setId(1L);

        Delivery delivery = new Delivery();
        delivery.setCustomer(customer);

        // When
        when(deliveryRepository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(delivery));

        Delivery foundDelivery = deliveryRequestService.findById(1L);

        // Then
        verify(deliveryRepository, times(1)).findById(anyLong());
        assert(foundDelivery.getCustomer().getId().equals(1L));
    }
}