package com.deliveryapi.controller;

import com.deliveryapi.domain.delivery.Delivery;
import com.deliveryapi.domain.delivery.DeliveryRequest;
import com.deliveryapi.application.DeliveryFinishService;
import com.deliveryapi.application.DeliveryRequestService;
import com.deliveryapi.domain.delivery.DeliveryResponse;
import com.deliveryapi.domain.delivery.DeliveryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/delivery")
public class DeliveryController {

    private final DeliveryRequestService deliveryRequestService;
    private final DeliveryFinishService deliveryFinishService;
    private final DeliveryMapper mapper = DeliveryMapper.INSTANCE;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeliveryResponse requestDelivery(@Valid @RequestBody DeliveryRequest deliveryRequest) {

         Delivery requestDelivery = deliveryRequestService.request(mapper.deliveryRequestToDelivery(deliveryRequest));

         return mapper.deliveryToDeliveryResponse(requestDelivery);
    }

    @GetMapping
    public List<DeliveryResponse> findAll() {
        return mapper.deliveryListToDeliveryResponseList(deliveryRequestService.findAll());
    }

    @GetMapping("/{deliveryId}")
    public DeliveryResponse findById(@PathVariable Long deliveryId) {
        return mapper.deliveryToDeliveryResponse(deliveryRequestService.findById(deliveryId));
    }

    @PutMapping("/{deliveryId}/finish")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finish(@PathVariable Long deliveryId) {
        deliveryFinishService.finish(deliveryId);
    }
}
