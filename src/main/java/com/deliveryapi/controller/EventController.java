package com.deliveryapi.controller;

import com.deliveryapi.domain.event.EventRequest;
import com.deliveryapi.domain.event.EventResponse;
import com.deliveryapi.domain.delivery.Delivery;
import com.deliveryapi.application.DeliveryRequestService;
import com.deliveryapi.application.EventRegisterService;
import com.deliveryapi.domain.event.EventMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/delivery/{deliveryId}/event")
@Api(tags = {"Event"})
public class EventController {

    private final DeliveryRequestService deliveryRequestService;
    private final EventRegisterService eventRegisterService;
    private final EventMapper mapper;

    @ApiOperation(value = "Register an event for a delivery")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Event registered successfully", response = EventResponse.class),
            @ApiResponse(code = 400, message = "Invalid event description")})
    @PostMapping
    public EventResponse register(@PathVariable Long deliveryId,
                                  @Valid @RequestBody EventRequest eventRequest) {

        return mapper.eventToEventResponse(
                eventRegisterService.register(deliveryId, eventRequest.description()));
    }

    @ApiOperation(value = "List all events for a delivery", notes = "Returns a list of all events for the specified delivery")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of events"),
            @ApiResponse(code = 404, message = "Delivery not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping
    public List<EventResponse> list(@PathVariable Long deliveryId) {

        Delivery delivery = deliveryRequestService.findById(deliveryId);

        return mapper.eventListToEventResponseList(delivery.getEvents());
    }
}
