package com.deliveryapi.application;

import com.deliveryapi.domain.delivery.Delivery;
import com.deliveryapi.domain.event.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class EventRegisterService {

    private final DeliveryRequestService deliveryRequestService;

    @Transactional
    public Event register(Long deliveryId, String description) {
        Delivery delivery = deliveryRequestService.findById(deliveryId);

        return delivery.addEvent(description);
    }
}
