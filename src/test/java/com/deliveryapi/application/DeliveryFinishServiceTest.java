package com.deliveryapi.application;

import com.deliveryapi.domain.delivery.Delivery;
import com.deliveryapi.domain.delivery.DeliveryRepository;
import com.deliveryapi.domain.delivery.DeliveryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class DeliveryFinishServiceTest {

    @Mock
    private DeliveryRepository deliveryRepository;

    @Mock
    private DeliveryRequestService deliveryRequestService;

    @InjectMocks
    private DeliveryFinishService deliveryFinishService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFinish() {
        // Given
        Delivery delivery = new Delivery();
        delivery.setStatus(DeliveryStatus.PENDING);
        delivery.setRequestDate(OffsetDateTime.now());

        // When
        when(deliveryRequestService.findById(anyLong())).thenReturn(delivery);
        when(deliveryRepository.save(delivery)).thenReturn(delivery);

        deliveryFinishService.finish(1L);

        // Then
        verify(deliveryRequestService, times(1)).findById(anyLong());
        verify(deliveryRepository, times(1)).save(delivery);
        assert(delivery.getStatus().equals(DeliveryStatus.FINISHED));
        assert(delivery.getDeliveredDate() != null);
    }
}

