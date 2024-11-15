package com.example.ecommerce_app.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.ecommerce_app.model.Product;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    void shouldCreateProduct() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setPrice(new BigDecimal("99.99"));

        Product saved = productService.save(product);
        assertNotNull(saved.getId());
    }
}
