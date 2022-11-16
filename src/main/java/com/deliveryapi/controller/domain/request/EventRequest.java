package com.deliveryapi.controller.domain.request;

import javax.validation.constraints.NotBlank;

public record EventRequest(@NotBlank String description) {
}
