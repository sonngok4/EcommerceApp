package com.example.ecommerce_app.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminApiController {
    
    @PostMapping("/add-product")
    public String createProduct() {
        return "Product created";
    }
}
