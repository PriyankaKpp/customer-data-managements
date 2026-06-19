package com.example.customer.service;

import com.example.customer.model.Customer;
import com.example.customer.repo.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {
    @Test
    void createAndGet() {
        CustomerRepository repo = Mockito.mock(CustomerRepository.class);
        CustomerService svc = new CustomerService(repo);
        Customer c = new Customer();
        c.setEmail("a@b.com");
        Mockito.when(repo.save(Mockito.any())).thenAnswer(i -> i.getArguments()[0]);
        Customer created = svc.create(c);
        assertNotNull(created.getCustomerId());
        Mockito.when(repo.findById(created.getCustomerId())).thenReturn(Optional.of(created));
        Optional<Customer> found = svc.get(created.getCustomerId());
        assertTrue(found.isPresent());
        assertEquals("a@b.com", found.get().getEmail());
    }
}
