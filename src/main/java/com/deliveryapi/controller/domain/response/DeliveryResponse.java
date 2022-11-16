package com.deliveryapi.controller.domain.response;

import com.deliveryapi.domain.model.DeliveryStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record DeliveryResponse (Long id,
                               CustomerResponse customer,
                               RecipientResponse recipient,
                               BigDecimal cost,
                               DeliveryStatus status,
                               OffsetDateTime requestDate,
                               OffsetDateTime deliveredDate) {

    @Builder public DeliveryResponse { }
}
