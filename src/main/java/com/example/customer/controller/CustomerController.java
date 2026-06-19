
package com.example.customer.controller;

import com.example.customer.model.Customer;
import com.example.customer.model.ApiResponse;
import com.example.customer.service.CustomerService;
import com.example.customer.ai.TokenEstimatorService;
import com.example.customer.ai.CostCalculatorService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService svc;

    @Autowired
    private TokenEstimatorService tokenEstimator;

    @Autowired
    private CostCalculatorService costCalculator;

    @Autowired
    private ObjectMapper objectMapper;

    public CustomerController(CustomerService svc) {
        this.svc = svc;
    }

    @PostMapping
    public ApiResponse<Customer> create(@RequestBody Customer c) throws Exception {

        String inputJson = objectMapper.writeValueAsString(c);
        int inputTokens = tokenEstimator.estimateTokens(inputJson);

        Customer created = svc.create(c);

        String outputJson = objectMapper.writeValueAsString(created);
        int outputTokens = tokenEstimator.estimateTokens(outputJson);

        double cost = costCalculator.calculateCost(inputTokens, outputTokens);

        return new ApiResponse<>(created, inputTokens + outputTokens, cost);
    }


    //  GET ALL
    @GetMapping
    public ApiResponse<List<Customer>> list() throws Exception {

        List<Customer> customers = svc.list();

        String json = objectMapper.writeValueAsString(customers);

        int tokens = tokenEstimator.estimateTokens(json);
        double cost = costCalculator.calculateCost(tokens, tokens);

        return new ApiResponse<>(customers, tokens, cost);
    }

    //  GET BY ID
    @GetMapping("/{id}")
    public ApiResponse<Customer> get(@PathVariable String id) throws Exception {

        Optional<Customer> found = svc.get(id);

        if (found.isEmpty()) {
            return new ApiResponse<>(null, 0, 0);
        }

        String json = objectMapper.writeValueAsString(found.get());
        int tokens = tokenEstimator.estimateTokens(json);
        double cost = costCalculator.calculateCost(tokens, tokens);

        return new ApiResponse<>(found.get(), tokens, cost);
    }

    //  UPDATE
    @PutMapping("/{id}")
    public ApiResponse<Customer> update(@PathVariable String id, @RequestBody Customer patch) throws Exception {

        Optional<Customer> updated = svc.update(id, patch);

        if (updated.isEmpty()) {
            return new ApiResponse<>(null, 0, 0);
        }

        String json = objectMapper.writeValueAsString(updated.get());
        int tokens = tokenEstimator.estimateTokens(json);
        double cost = costCalculator.calculateCost(tokens, tokens);

        return new ApiResponse<>(updated.get(), tokens, cost);
    }

    //  DELETE
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable String id) {

        svc.delete(id);

        return new ApiResponse<>("Deleted successfully", 10, 0.00003);
    }
}
