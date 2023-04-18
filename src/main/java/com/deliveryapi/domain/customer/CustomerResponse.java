package com.deliveryapi.domain.customer;

import lombok.Builder;

public record CustomerResponse (Long id,
                               String name,
                               String email,
                               String phone) {
    @Builder public CustomerResponse { }
}
