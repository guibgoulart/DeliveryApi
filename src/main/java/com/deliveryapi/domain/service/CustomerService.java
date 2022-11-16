package com.deliveryapi.domain.service;

import com.deliveryapi.domain.exception.DomainException;
import com.deliveryapi.domain.exception.NotFoundException;
import com.deliveryapi.domain.model.Customer;
import com.deliveryapi.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Transactional
    public Customer save(Customer customer) {
        boolean emailAlreadyRegistered = customerRepository.findByEmail(customer.getEmail())
                .stream()
                .anyMatch(existingCustomer -> !existingCustomer.equals(customer));

        if (emailAlreadyRegistered) {
            throw new DomainException("Email already registered");
        }

        return customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer findById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    public boolean existsById(Long customerId) {
        if (customerRepository.existsById(customerId)) {
            return true;
        } else throw new NotFoundException("Customer not found");
    }

    @Transactional
    public Customer update(Long customerId, Customer customer) {

        Customer customerToUpdate = new Customer();

        if(existsById(customerId)) {
            customerToUpdate = customerRepository.getReferenceById(customerId);
            customerToUpdate.setName(customer.getName());
            customerToUpdate.setEmail(customer.getEmail());
            customerToUpdate.setPhone(customer.getPhone());
        }

        return customerRepository.save(customerToUpdate);
    }

    @Transactional
    public void deleteById(Long customerId) {
        if(existsById(customerId)) customerRepository.deleteById(customerId);
    }
}
