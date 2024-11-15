package com.example.ecommerce_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("")
    public String admin() {
        return "admin/index";
    }

    @GetMapping("/accounts")
    public String accounts() {
        return "admin/accounts";
    }

    @GetMapping("/login")
    public String admin_login() {
        return "admin/login";
    }

    @GetMapping("/products")
    public String products() {
        return "admin/products";
    }

    @GetMapping("/add-product")
    public String add_product() {
        return "admin/add-product";
    }

    @GetMapping("/edit-product")
    public String edit_product() {
        return "admin/edit-product";
    }

    @GetMapping("/orders")
    public String orders() {
        return "admin/orders";
    }

    @GetMapping("/reviews")
    public String reviews() {
        return "admin/reviews";
    }

    @GetMapping("/users")
    public String users() {
        return "admin/users";
    }

    @GetMapping("/categories")
    public String categories() {
        return "admin/categories";
    }

}
