package com.deliveryapi.domain.delivery;

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
