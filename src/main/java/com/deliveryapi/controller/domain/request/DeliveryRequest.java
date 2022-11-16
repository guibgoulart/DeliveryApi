package com.deliveryapi.controller.domain.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record DeliveryRequest (
        @Valid @NotNull CustomerIdRequest customer,
        @Valid @NotNull RecipientRequest recipient,
        @NotNull BigDecimal cost) {
}

