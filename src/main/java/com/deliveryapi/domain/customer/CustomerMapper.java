package com.deliveryapi.domain.customer;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    CustomerResponse customerToCustomerResponse(Customer customer);

    Customer customerRequestToCustomer(CustomerRequest customerRequest);

    CustomerResponse customerRequestToCustomerResponse(CustomerRequest customerRequest);

    List<CustomerResponse> customerListToCustomerResponseList(Iterable<Customer> customerIterable);

}
