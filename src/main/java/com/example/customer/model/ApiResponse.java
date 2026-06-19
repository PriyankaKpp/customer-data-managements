package com.example.customer.model;

public class ApiResponse<T>{

    private T data;
    private int tokens;
    private double cost;

    public ApiResponse(T data, int tokens, double cost) {
        this.data = data;
        this.tokens = tokens;
        this.cost = cost;
    }

    public T getData() { return data; }
    public int getTokens() { return tokens; }
    public double getCost() { return cost; }
}
