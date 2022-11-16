package com.deliveryapi.domain.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Embeddable
public class Recipient {

    @Column(name = "recipient_name")
    private String name;

    @Column(name = "recipient_address")
    private String address;
}
