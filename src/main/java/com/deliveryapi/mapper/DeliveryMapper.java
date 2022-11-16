package com.deliveryapi.mapper;

import com.deliveryapi.controller.domain.response.DeliveryResponse;
import com.deliveryapi.domain.model.Delivery;
import com.deliveryapi.controller.domain.request.DeliveryRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DeliveryMapper {

    DeliveryMapper INSTANCE = Mappers.getMapper(DeliveryMapper.class);

    DeliveryResponse deliveryToDeliveryResponse(Delivery delivery);

    Delivery deliveryRequestToDelivery(DeliveryRequest deliveryRequest);

    List<DeliveryResponse> deliveryListToDeliveryResponseList(Iterable<Delivery> deliveryIterable);
}
