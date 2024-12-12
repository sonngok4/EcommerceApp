package com.example.ecommerce_app.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CategoryResponseDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
