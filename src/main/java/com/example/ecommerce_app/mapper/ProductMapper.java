// ProductMapper.java
package com.example.ecommerce_app.mapper;

import com.example.ecommerce_app.dto.ProductDTO;
import com.example.ecommerce_app.model.Product;
import com.example.ecommerce_app.model.Category;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }

        return new ProductDTO(
                product.getId(),
                product.getProductName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getCategoryName() : null,
                product.getImageUrl(),
                product.getCreatedAt(),
                product.getUpdatedAt());
    }

    public Product toEntity(ProductDTO dto) {
        if (dto == null) {
            return null;
        }

        Product product = new Product();
        product.setId(dto.getId());
        product.setProductName(dto.getProductName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setImageUrl(dto.getImageUrl());

        // Note: Category needs to be set separately as it requires the actual Category
        // entity
        // This is typically done in the service layer

        if (dto.getCreatedAt() != null) {
            product.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            product.setUpdatedAt(dto.getUpdatedAt());
        }

        return product;
    }

    public void updateEntityFromDTO(ProductDTO dto, Product product) {
        if (dto == null || product == null) {
            return;
        }

        if (dto.getProductName() != null) {
            product.setProductName(dto.getProductName());
        }
        if (dto.getDescription() != null) {
            product.setDescription(dto.getDescription());
        }
        if (dto.getPrice() != null) {
            product.setPrice(dto.getPrice());
        }
        if (dto.getStock() != null) {
            product.setStock(dto.getStock());
        }
        if (dto.getImageUrl() != null) {
            product.setImageUrl(dto.getImageUrl());
        }
        // Note: Category update should be handled separately in service layer
    }

    public void setCategory(Product product, Category category) {
        if (product != null) {
            product.setCategory(category);
        }
    }
}