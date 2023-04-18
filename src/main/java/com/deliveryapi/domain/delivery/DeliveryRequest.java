package com.deliveryapi.domain.delivery;

import com.deliveryapi.domain.recipient.RecipientRequest;
import com.deliveryapi.domain.customer.CustomerIdRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record DeliveryRequest (
        @Valid @NotNull CustomerIdRequest customer,
        @Valid @NotNull RecipientRequest recipient,
        @NotNull BigDecimal cost) {
}

