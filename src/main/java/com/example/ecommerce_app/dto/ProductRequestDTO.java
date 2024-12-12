// ProductDTO.java
package com.example.ecommerce_app.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ProductRequestDTO {
    private Long id;
    private Long categoryId;
    private String productName;
    private MultipartFile image;
    private BigDecimal price;
    private Integer stockQuantity;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
