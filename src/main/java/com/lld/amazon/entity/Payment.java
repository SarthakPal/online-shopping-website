package com.lld.amazon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;

    private String currency;

    private String paymentMethod;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime paymentDate;

    private String transactionId;

    private String paymentStatus; // e.g., "SUCCESS", "FAILED"

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String cardNumber;

    private String cardHolderName;

    private String expiryDate;

    private String cvv;

}

