package com.deliveryapi.domain.delivery;

import com.deliveryapi.domain.recipient.RecipientResponse;
import com.deliveryapi.domain.customer.CustomerResponse;
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
