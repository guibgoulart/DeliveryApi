package com.deliveryapi.domain.recipient;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface RecipientMapper {

    RecipientMapper INSTANCE = Mappers.getMapper(RecipientMapper.class);

    Recipient recipientToCustomerModel(Recipient recipient);
}
