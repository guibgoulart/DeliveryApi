package com.deliveryapi.controller;

import com.deliveryapi.domain.delivery.Delivery;
import com.deliveryapi.domain.delivery.DeliveryRequest;
import com.deliveryapi.application.DeliveryFinishService;
import com.deliveryapi.application.DeliveryRequestService;
import com.deliveryapi.domain.delivery.DeliveryResponse;
import com.deliveryapi.domain.delivery.DeliveryMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/delivery")
@Api(tags = "Deliveries")
public class DeliveryController {

    private final DeliveryRequestService deliveryRequestService;
    private final DeliveryFinishService deliveryFinishService;
    private final DeliveryMapper mapper = DeliveryMapper.INSTANCE;

    @ApiOperation("Requests a new delivery")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Delivery requested"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DeliveryResponse requestDelivery(@Valid @RequestBody DeliveryRequest deliveryRequest) {

        Delivery requestDelivery = deliveryRequestService.request(mapper.deliveryRequestToDelivery(deliveryRequest));

        return mapper.deliveryToDeliveryResponse(requestDelivery);
    }

    @ApiOperation("Returns all deliveries")
    @ApiResponses({
            @ApiResponse(code = 200, message = "List of deliveries returned"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping
    public List<DeliveryResponse> findAll() {
        return mapper.deliveryListToDeliveryResponseList(deliveryRequestService.findAll());
    }

    @ApiOperation("Returns a delivery by ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Delivery returned"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Delivery not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping("/{deliveryId}")
    public DeliveryResponse findById(@PathVariable Long deliveryId) {
        return mapper.deliveryToDeliveryResponse(deliveryRequestService.findById(deliveryId));
    }

    @ApiOperation("Finishes a delivery")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Delivery finished"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 404, message = "Delivery not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping("/{deliveryId}/finish")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void finish(@PathVariable Long deliveryId) {
        deliveryFinishService.finish(deliveryId);
    }
}
