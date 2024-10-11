package com.example.ecommerce_app.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecommerce_app.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByProductName(String productName);
}