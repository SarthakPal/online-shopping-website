package com.lld.amazon.dto;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class CardDetails {

    private String cardNumber;

    private String cardHolderName;

    private String expiryDate;

    private String cvv;

}

