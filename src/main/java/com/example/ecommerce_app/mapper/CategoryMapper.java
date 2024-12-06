// CategoryMapper.java
package com.example.ecommerce_app.mapper;

import com.example.ecommerce_app.dto.CategoryDTO;
import com.example.ecommerce_app.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDTO toDTO(Category category) {
        if (category == null) {
            return null;
        }

        return new CategoryDTO(
                category.getId(),
                category.getCategoryName(),
                category.getDescription(),
                category.getCreatedAt(),
                category.getUpdatedAt());
    }

    public Category toEntity(CategoryDTO dto) {
        if (dto == null) {
            return null;
        }

        Category category = new Category();
        category.setId(dto.getId());
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());

        // Only set creation/update times if they're present in the DTO
        if (dto.getCreatedAt() != null) {
            category.setCreatedAt(dto.getCreatedAt());
        }
        if (dto.getUpdatedAt() != null) {
            category.setUpdatedAt(dto.getUpdatedAt());
        }

        return category;
    }

    public void updateEntityFromDTO(CategoryDTO dto, Category category) {
        if (dto == null || category == null) {
            return;
        }

        if (dto.getCategoryName() != null) {
            category.setCategoryName(dto.getCategoryName());
        }
        if (dto.getDescription() != null) {
            category.setDescription(dto.getDescription());
        }
        // Note: We typically don't update createdAt/updatedAt from DTO
        // as these are managed by the entity lifecycle
    }
}