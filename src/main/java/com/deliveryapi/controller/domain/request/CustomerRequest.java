package com.deliveryapi.controller.domain.request;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record CustomerRequest (@NotBlank @Size(max = 60) String name,
                               @NotBlank @Email @Size(max = 255) String email,
                               @NotBlank @Size(max = 20) String phone) {
    @Builder
    public CustomerRequest { }
}
