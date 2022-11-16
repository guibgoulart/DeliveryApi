package com.deliveryapi.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(Include.NON_NULL)
public record Error(Integer status, OffsetDateTime dateTime, String title, List<Field> fields) {
    @Builder public Error { }

    record Field(String name, String message) {
        @Builder public Field { }
    }
}
