package com.deliveryapi.mapper;


import com.deliveryapi.controller.domain.request.EventRequest;
import com.deliveryapi.controller.domain.response.EventResponse;
import com.deliveryapi.domain.model.Event;
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
