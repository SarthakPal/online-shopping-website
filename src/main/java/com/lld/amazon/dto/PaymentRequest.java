package com.lld.amazon.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private double amount;
    private String currency;
    private String paymentMethod; // e.g., "CARD", "UPI"
    private CardDetails cardDetails;
}

