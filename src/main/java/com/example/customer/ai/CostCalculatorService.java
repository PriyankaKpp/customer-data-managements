package com.example.customer.ai;

import org.springframework.stereotype.Service;

@Service
public class CostCalculatorService {

    //  Claude pricing 
    private static final double INPUT_COST_PER_1K = 0.003;
    private static final double OUTPUT_COST_PER_1K = 0.006;

    public double calculateCost(int inputTokens, int outputTokens) {
        double inputCost = (inputTokens / 1000.0) * INPUT_COST_PER_1K;
        double outputCost = (outputTokens / 1000.0) * OUTPUT_COST_PER_1K;
       
        double totalCost = inputCost + outputCost;
        return Math.round(totalCost * 1000000.0) / 1000000.0;
    }
}