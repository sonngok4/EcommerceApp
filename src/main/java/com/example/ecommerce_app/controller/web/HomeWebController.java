package com.example.ecommerce_app.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_app.model.Product;
import com.example.ecommerce_app.service.ProductService;

@Controller
@RequestMapping("/")
public class HomeWebController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public String homePage(Model model) {
        List<Product> products = productService.findAll();
        int limit = 3;
        if (products.size() > limit) {
            products = products.subList(0, limit); // Lấy 10 sản phẩm đầu tiên
        }
        model.addAttribute("products", products);
        return "pages/index";
    }

    @GetMapping("/about")
    public String about() {
        return "pages/about";
    }

    @GetMapping("/detail")
    public String detail() {
        return "pages/detail";
    }

    @GetMapping("/cart")
    @PreAuthorize("isAuthenticated()")
    public String cart() {
        return "pages/cart";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "pages/checkout";
    }

    @GetMapping("/contact")
    public String contact() {
        return "pages/contact";
    }

    @GetMapping("/services")
    public String service() {
        return "pages/services";
    }

    @GetMapping("/blog")
    public String blog() {
        return "pages/blog";
    }

}
