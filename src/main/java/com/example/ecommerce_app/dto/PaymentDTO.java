package com.example.ecommerce_app.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PaymentDTO {
    private Long orderId;
    private BigDecimal amount;
    private String paymentMethod;
    private String status;
}