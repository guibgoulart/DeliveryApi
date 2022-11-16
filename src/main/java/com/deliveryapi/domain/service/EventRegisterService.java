package com.deliveryapi.domain.service;

import com.deliveryapi.domain.model.Delivery;
import com.deliveryapi.domain.model.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
