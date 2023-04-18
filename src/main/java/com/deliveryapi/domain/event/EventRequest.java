package com.deliveryapi.domain.event;

import javax.validation.constraints.NotBlank;

public record EventRequest(@NotBlank String description) {
}
