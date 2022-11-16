package com.deliveryapi.controller.domain.response;

import lombok.Builder;

public record CustomerResponse (Long id,
                               String name,
                               String email,
                               String phone) {
    @Builder public CustomerResponse { }
}
