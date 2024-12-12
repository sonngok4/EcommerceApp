package com.example.ecommerce_app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private Long id;
    private String name;
    private CategoryResponseDTO category;
    private String image;
    private BigDecimal price;
    private Integer stockQuantity;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
