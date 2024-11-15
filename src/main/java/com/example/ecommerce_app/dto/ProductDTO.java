package com.example.ecommerce_app.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private Integer stockQuantity;
    private List<String> images;
}