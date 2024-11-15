package com.example.ecommerce_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce_app.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductName(String productName);
}