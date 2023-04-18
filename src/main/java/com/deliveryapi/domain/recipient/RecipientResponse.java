package com.deliveryapi.domain.recipient;

import lombok.Builder;

public record RecipientResponse (String name, String address){
    @Builder public RecipientResponse { }
}
