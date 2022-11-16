package com.deliveryapi.controller;

import com.deliveryapi.controller.domain.request.EventRequest;
import com.deliveryapi.controller.domain.response.EventResponse;
import com.deliveryapi.domain.model.Delivery;
import com.deliveryapi.domain.model.Event;
import com.deliveryapi.domain.service.DeliveryRequestService;
import com.deliveryapi.domain.service.EventRegisterService;
import com.deliveryapi.mapper.EventMapper;
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
