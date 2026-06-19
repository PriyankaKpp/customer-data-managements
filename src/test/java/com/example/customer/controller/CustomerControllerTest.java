package com.example.customer.controller;

import com.example.customer.ai.CostCalculatorService;
import com.example.customer.ai.TokenEstimatorService;
import com.example.customer.model.Customer;
import com.example.customer.service.CustomerService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService svc;

    // ✅ REQUIRED because controller uses them
    @MockBean
    private TokenEstimatorService tokenEstimator;

    @MockBean
    private CostCalculatorService costCalculator;


    @Test
    void getNotFound() throws Exception {

        String id = UUID.randomUUID().toString();

        // Mock service returning empty
        Mockito.when(svc.get(id)).thenReturn(Optional.empty());

        // Mock token + cost services (safe defaults)
        Mockito.when(tokenEstimator.estimateTokens(Mockito.anyString())).thenReturn(0);
        Mockito.when(costCalculator.calculateCost(Mockito.anyInt(), Mockito.anyInt())).thenReturn(0.0);

        mvc.perform(
                get("/api/customers/" + id)
                        .header("Authorization", "Bearer changeme")
                        .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());   // ✅ IMPORTANT (not 404 anymore)
    }
}
