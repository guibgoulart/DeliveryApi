package com.deliveryapi.controller;

import com.deliveryapi.controller.domain.request.CustomerRequest;
import com.deliveryapi.controller.domain.response.CustomerResponse;
import com.deliveryapi.domain.model.Customer;
import com.deliveryapi.domain.service.CustomerService;
import com.deliveryapi.mapper.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper mapper = CustomerMapper.INSTANCE;

    @GetMapping
    public List<CustomerResponse> findAll() {
        return mapper.customerListToCustomerResponseList(customerService.findAll());
    }

    @GetMapping("/{customerId}")
    public CustomerResponse findById(@PathVariable Long customerId) {
        return mapper.customerToCustomerResponse(customerService.findById(customerId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse save(@Valid @RequestBody CustomerRequest customer) {
        return mapper.customerToCustomerResponse(
                customerService.save(mapper.customerRequestToCustomer(customer))
        );
    }

    @PutMapping("/{customerId}")
    public CustomerResponse update(@PathVariable Long customerId,
                                           @Valid @RequestBody CustomerRequest customer) {

        Customer updatedCostumer = customerService.update(customerId, mapper.customerRequestToCustomer(customer));
        return mapper.customerToCustomerResponse(updatedCostumer);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> delete(@PathVariable Long customerId) {
        customerService.deleteById(customerId);
        return ResponseEntity.noContent().build();
    }
}