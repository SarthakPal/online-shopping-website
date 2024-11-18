package com.lld.amazon.dto;

import lombok.Data;

@Data
public class PaymentResponse {
    private String transactionId;
    private String status; // e.g., "SUCCESS", "FAILED"
    private String message;
}

