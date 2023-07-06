package com.deliveryapi.application;

import com.deliveryapi.domain.delivery.Delivery;
import com.deliveryapi.domain.event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class EventRegisterServiceTest {

    @Mock
    private DeliveryRequestService deliveryRequestService;

    @InjectMocks
    private EventRegisterService eventRegisterService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        // Given
        Long deliveryId = 1L;
        String description = "Test Event";
        Delivery mockDelivery = new Delivery();

        // When
        when(deliveryRequestService.findById(anyLong())).thenReturn(mockDelivery);

        Event event = eventRegisterService.register(deliveryId, description);

        // Then
        assertNotNull(event);
    }
}