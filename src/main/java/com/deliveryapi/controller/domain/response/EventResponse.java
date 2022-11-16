package com.deliveryapi.controller.domain.response;

import java.time.OffsetDateTime;

public record EventResponse (Long id, String description, OffsetDateTime eventDate) {
}
