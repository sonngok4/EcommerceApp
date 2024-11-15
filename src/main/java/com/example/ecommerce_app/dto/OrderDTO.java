package com.example.ecommerce_app.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class OrderDTO {
    private Long id;
    private Long userId;
    private List<OrderItemDTO> items;
    private BigDecimal totalAmount;
    private String status;
    private String shippingAddress;
}