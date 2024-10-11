package com.example.ecommerce_app.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.example.ecommerce_app.models.Product;
import com.example.ecommerce_app.services.ProductService;

@Controller
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 1. API get all products
    @GetMapping
    @ResponseBody
    public List<Product> getListProducts() {
        System.out.println("------>controller/products/getListProducts");
        return productService.findAll();
    }

    // 2. API get product by id
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) {
        Product product = productService.findById(id);
        if (product != null) {
            return ResponseEntity.status(200).body(product);
        }
        return ResponseEntity.status(404).body(null);
    }

    // 3. API delete product by id
    @DeleteMapping("/{id}")
    @ResponseBody
    public List<Product> removeProductById(@PathVariable("id") long id) {
        productService.delete(id);
        return productService.findAll();
    }

    // 4. API POST new product
    @PostMapping
    @ResponseBody
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        if (!productService.isProductNameUnique(product.getProductName())) {
            return ResponseEntity.status(400).body("Product name already exists");
        }
        productService.save(product);
        return ResponseEntity.status(201).body(product);
    }

    // 5. API PUT product by id
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product updateProduct) {
        Product product = productService.findById(id);
        if (product == null) {
            return ResponseEntity.status(404).body(null);
        }

        product.setProductName(updateProduct.getProductName());
        product.setDescription(updateProduct.getDescription());
        product.setPrice(updateProduct.getPrice());
        product.setStock(updateProduct.getStock());
        product.setCategory(updateProduct.getCategory());
        product.setImageUrl(updateProduct.getImageUrl());

        productService.save(product);

        return ResponseEntity.status(200).body(product);
    }
}