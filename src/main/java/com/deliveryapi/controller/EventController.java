package com.deliveryapi.controller;

import com.deliveryapi.domain.event.EventRequest;
import com.deliveryapi.domain.event.EventResponse;
import com.deliveryapi.domain.delivery.Delivery;
import com.deliveryapi.application.DeliveryRequestService;
import com.deliveryapi.application.EventRegisterService;
import com.deliveryapi.domain.event.EventMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/delivery/{deliveryId}/event")
public class EventController {

    private final DeliveryRequestService deliveryRequestService;
    private final EventRegisterService eventRegisterService;
    private final EventMapper mapper;

    @PostMapping
    public EventResponse register(@PathVariable Long deliveryId,
                                  @Valid @RequestBody EventRequest eventRequest) {

        return mapper.eventToEventResponse(
                eventRegisterService.register(deliveryId, eventRequest.description()));
    }

    @GetMapping
    public List<EventResponse> list(@PathVariable Long deliveryId) {

        Delivery delivery = deliveryRequestService.findById(deliveryId);

        return mapper.eventListToEventResponseList(delivery.getEvents());
    }
}
