package com.deliveryapi.domain.event;

import java.time.OffsetDateTime;

public record EventResponse (Long id, String description, OffsetDateTime eventDate) {
}
