package com.deliveryapi.domain.exception;

import java.io.Serial;

public class NotFoundException extends DomainException {

    @Serial
    private static final long serialVersionUID = 2L;

    public NotFoundException(String message) {
        super(message);
    }
}
