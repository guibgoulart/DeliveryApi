package com.deliveryapi.controller;

import com.deliveryapi.domain.customer.CustomerRequest;
import com.deliveryapi.domain.customer.CustomerResponse;
import com.deliveryapi.domain.customer.Customer;
import com.deliveryapi.application.CustomerService;
import com.deliveryapi.domain.customer.CustomerMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
@Api(tags = "Customer")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper mapper = CustomerMapper.INSTANCE;

    @ApiOperation(value = "Get all customers", response = CustomerResponse.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @GetMapping
    public List<CustomerResponse> findAll() {
        return mapper.customerListToCustomerResponseList(customerService.findAll());
    }

    @ApiOperation(value = "Get a customer by ID", response = CustomerResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved customer"),
            @ApiResponse(code = 404, message = "The customer you were trying to reach is not found")
    })
    @GetMapping("/{customerId}")
    public CustomerResponse findById(@PathVariable Long customerId) {
        return mapper.customerToCustomerResponse(customerService.findById(customerId));
    }

    @ApiOperation(value = "Add a new customer", response = CustomerResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse save(@Valid @RequestBody CustomerRequest customer) {
        return mapper.customerToCustomerResponse(
                customerService.save(mapper.customerRequestToCustomer(customer))
        );
    }
    @ApiOperation(value = "Update an existing customer", response = CustomerResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @PutMapping("/{customerId}")
    public CustomerResponse update(@PathVariable Long customerId,
                                           @Valid @RequestBody CustomerRequest customer) {

        Customer updatedCostumer = customerService.update(customerId, mapper.customerRequestToCustomer(customer));
        return mapper.customerToCustomerResponse(updatedCostumer);
    }
    @ApiOperation(value = "Delete a customer by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> delete(@PathVariable Long customerId) {
        customerService.deleteById(customerId);
        return ResponseEntity.noContent().build();
    }
}