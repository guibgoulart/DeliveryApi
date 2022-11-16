package com.deliveryapi.domain.exception;

import java.io.Serial;

public class DomainException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DomainException(String message) {
        super(message);
    }
}
