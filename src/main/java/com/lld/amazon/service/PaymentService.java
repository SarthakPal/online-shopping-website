package com.lld.amazon.service;

import com.lld.amazon.dto.PaymentRequest;
import com.lld.amazon.dto.PaymentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    @Value("${payment.gateway.url}")
    private String paymentGatewayUrl;

    @Value("${payment.gateway.apiKey}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public PaymentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean processPayment(PaymentRequest paymentRequest) {
        try {

            // Set headers (e.g., API key for authentication)
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<PaymentRequest> request = new HttpEntity<>(paymentRequest, headers);

            // Send request to payment gateway
            PaymentResponse response = restTemplate.postForObject(
                    paymentGatewayUrl + "/payments",
                    request,
                    PaymentResponse.class
            );

            // Process response
            return response != null && "SUCCESS".equalsIgnoreCase(response.getStatus());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

