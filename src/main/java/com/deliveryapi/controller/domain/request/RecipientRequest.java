package com.deliveryapi.controller.domain.request;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

public record RecipientRequest(@NotBlank String name, @NotBlank String address) {
    @Builder public RecipientRequest { }
}
