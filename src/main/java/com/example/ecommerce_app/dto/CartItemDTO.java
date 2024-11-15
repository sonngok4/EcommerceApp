package com.example.ecommerce_app.dto;

import lombok.Data;

@Data
public class CartItemDTO {
    private Long productId;
    private Integer quantity;
}
