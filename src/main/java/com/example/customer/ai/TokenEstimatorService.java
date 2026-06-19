package com.example.customer.ai;

import org.springframework.stereotype.Service;

@Service
public class TokenEstimatorService {

    // 1 token ≈ 4 characters (Claude estimation)
    public int estimateTokens(String text) {
        if (text == null) return 0;
        return text.length() / 4;
    }
}
