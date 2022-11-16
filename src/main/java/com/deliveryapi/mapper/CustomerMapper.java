package com.deliveryapi.mapper;

import com.deliveryapi.controller.domain.request.CustomerRequest;
import com.deliveryapi.controller.domain.response.CustomerResponse;
import com.deliveryapi.domain.model.Customer;
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
