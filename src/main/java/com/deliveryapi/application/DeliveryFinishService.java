package com.deliveryapi.application;

import com.deliveryapi.domain.delivery.Delivery;
import com.deliveryapi.domain.delivery.DeliveryRepository;
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
