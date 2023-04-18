package com.deliveryapi.domain.recipient;

import lombok.Builder;

import javax.validation.constraints.NotBlank;

public record RecipientRequest(@NotBlank String name, @NotBlank String address) {
    @Builder public RecipientRequest { }
}
