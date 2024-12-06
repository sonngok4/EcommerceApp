// ImageRepository.java
package com.example.ecommerce_app.repository;

import com.example.ecommerce_app.model.ImageMetadata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageMetadata, Long> {
    // Additional custom query methods can be added here if needed
}