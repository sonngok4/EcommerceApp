package com.example.ecommerce_app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.example.ecommerce_app.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductName(String productName);

    List<Product> findAllByOrderByCreatedAtDesc();

    // List<Product> findByCategoryName(@Param("categoryName") String categoryName);

    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);
}