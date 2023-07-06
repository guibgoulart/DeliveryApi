package com.deliveryapi.application;

import com.deliveryapi.domain.customer.Customer;
import com.deliveryapi.domain.customer.CustomerRepository;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerServiceTest {

    CustomerRepository customerRepository = mock(CustomerRepository.class);
    CustomerService customerService = new CustomerService(customerRepository);

    @Test
    void testSave() {
        Customer customer = new Customer();
        customer.setEmail("test@example.com");

        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(customerRepository.save(any())).thenReturn(customer);

        Customer savedCustomer = customerService.save(customer);

        assertEquals(customer, savedCustomer);
        verify(customerRepository, times(1)).findByEmail(anyString());
        verify(customerRepository, times(1)).save(any());
    }


    @Test
    void testFindAll() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(new Customer()));

        List<Customer> customers = customerService.findAll();

        assertEquals(1, customers.size());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Long id = 1L;
        Customer customer = new Customer();
        customer.setId(id);

        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));

        Customer foundCustomer = customerService.findById(id);

        assertEquals(customer, foundCustomer);
        verify(customerRepository, times(1)).findById(id);
    }

    @Test
    void testExistsById() {
        Long id = 1L;

        when(customerRepository.existsById(id)).thenReturn(true);

        boolean exists = customerService.existsById(id);

        assertTrue(exists);
        verify(customerRepository, times(1)).existsById(id);
    }

    @Test
    void testUpdate() {
        Long id = 1L;
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName("Updated Name");

        when(customerRepository.existsById(id)).thenReturn(true);
        when(customerRepository.getReferenceById(id)).thenReturn(customer);
        when(customerRepository.save(any())).thenReturn(customer);

        Customer updatedCustomer = customerService.update(id, customer);

        assertEquals("Updated Name", updatedCustomer.getName());
        verify(customerRepository, times(1)).existsById(id);
        verify(customerRepository, times(1)).getReferenceById(id);
        verify(customerRepository, times(1)).save(any());
    }

    @Test
    void testDeleteById() {
        Long id = 1L;

        when(customerRepository.existsById(id)).thenReturn(true);
        doNothing().when(customerRepository).deleteById(id);

        customerService.deleteById(id);

        verify(customerRepository, times(1)).existsById(id);
        verify(customerRepository, times(1)).deleteById(id);
    }
}
