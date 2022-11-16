package com.deliveryapi.controller.domain.request;

import javax.validation.constraints.NotNull;

public record CustomerIdRequest(@NotNull Long id) {
}
