package com.example.customer.service;

import com.example.customer.model.Customer;
import com.example.customer.repo.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public Customer create(Customer c) {

        // ✅ FIX: UUID → String
        if (c.getCustomerId() == null) {
            c.setCustomerId(UUID.randomUUID().toString());
        }

        // ✅ FIX: OffsetDateTime → LocalDateTime
        c.setCreatedAt(LocalDateTime.now());
        c.setUpdatedAt(LocalDateTime.now());

        return repo.save(c);
    }

    // ✅ FIX: UUID → String
    public Optional<Customer> get(String id) {
        return repo.findById(id);
    }

    public List<Customer> list() {
        return repo.findAll();
    }

    // ✅ FIX: UUID → String
    public Optional<Customer> update(String id, Customer patch) {

        return repo.findById(id).map(existing -> {

            // minimal patch behaviour
            if (patch.getFirstName() != null)
                existing.setFirstName(patch.getFirstName());

            if (patch.getLastName() != null)
                existing.setLastName(patch.getLastName());

            if (patch.getEmail() != null)
                existing.setEmail(patch.getEmail());

            // ✅ FIX: OffsetDateTime → LocalDateTime
            existing.setUpdatedAt(LocalDateTime.now());

            return repo.save(existing);
        });
    }

    // ✅ FIX: UUID → String
    public void delete(String id) {
        repo.deleteById(id);
    }
}