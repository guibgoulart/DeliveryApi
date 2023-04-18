package com.deliveryapi.domain.customer;

import javax.validation.constraints.NotNull;

public record CustomerIdRequest(@NotNull Long id) {
}
