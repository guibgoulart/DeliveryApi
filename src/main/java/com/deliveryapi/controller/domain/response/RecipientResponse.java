package com.deliveryapi.controller.domain.response;

import lombok.Builder;

public record RecipientResponse (String name, String address){
    @Builder public RecipientResponse { }
}
