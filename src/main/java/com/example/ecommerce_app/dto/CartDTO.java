package com.example.ecommerce_app.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class CartDTO {
    private Long userId;
    private List<CartItemDTO> items;
    private BigDecimal totalAmount;
}
