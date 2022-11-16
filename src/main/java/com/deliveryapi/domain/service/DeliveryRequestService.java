package com.deliveryapi.domain.service;

import com.deliveryapi.domain.exception.DomainException;
import com.deliveryapi.domain.exception.NotFoundException;
import com.deliveryapi.domain.model.Customer;
import com.deliveryapi.domain.model.Delivery;
import com.deliveryapi.domain.model.DeliveryStatus;
import com.deliveryapi.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryRequestService {

    private final DeliveryRepository deliveryRepository;
    private final CustomerService customerService;

    @Transactional
    public Delivery request(Delivery delivery) {
        Customer customer = customerService.findById(delivery.getCustomer().getId());

        delivery.setCustomer(customer);
        delivery.setStatus(DeliveryStatus.PENDING);
        delivery.setRequestDate(OffsetDateTime.now());
        return deliveryRepository.save(delivery);
    }

    public List<Delivery> findAll() {
        return deliveryRepository.findAll();
    }

    public Delivery findById(Long deliveryId) {
        return deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new NotFoundException("Delivery not found"));
    }
}
