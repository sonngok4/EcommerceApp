package com.example.ecommerce_app.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_app.model.Product;
import com.example.ecommerce_app.model.User;
import com.example.ecommerce_app.service.ProductService;
import com.example.ecommerce_app.service.UserService;

@Controller
@RequestMapping("/")
public class HomeWebController {

    @Autowired
    private UserService userService;

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

    @GetMapping("/me")
    public String showProfilePage(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername(); // username with config for email

        User user = userService.getUserByEmail(email);
        model.addAttribute("username", user.getUsername());
        model.addAttribute("user", user);
        System.out.println("------>controller/auth/me");
        // Debugging
        return "pages/profile";
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
