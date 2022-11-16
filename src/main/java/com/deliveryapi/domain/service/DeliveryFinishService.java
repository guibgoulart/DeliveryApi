package com.deliveryapi.domain.service;

import com.deliveryapi.domain.model.Delivery;
import com.deliveryapi.domain.model.DeliveryStatus;
import com.deliveryapi.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DeliveryFinishService {

    private final DeliveryRepository deliveryRepository;
    private final DeliveryRequestService deliveryRequestService;

    @Transactional
    public void finish(Long deliveryId) {
        Delivery delivery = deliveryRequestService.findById(deliveryId);
        delivery.finish();

        deliveryRepository.save(delivery);
    }
}
