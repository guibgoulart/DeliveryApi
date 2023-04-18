package com.deliveryapi.domain.event;


import com.deliveryapi.domain.delivery.DeliveryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    DeliveryMapper INSTANCE = Mappers.getMapper(DeliveryMapper.class);

    EventResponse eventToEventResponse(Event event);

    Event eventRequestToEvent(EventRequest eventRequest);

    List<EventResponse> eventListToEventResponseList(Iterable<Event> eventIterable);
}
