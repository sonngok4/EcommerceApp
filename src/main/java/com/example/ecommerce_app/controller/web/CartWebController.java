// package com.example.ecommerce_app.controller.web;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;

// import com.example.ecommerce_app.model.Cart;
// import com.example.ecommerce_app.service.CartService;

// @Controller
// @RequestMapping("/cart")
// public class CartWebController {

//     @Autowired
//     private CartService cartService;

//     // Automatically checks authentication
//     @GetMapping
//     public String getCart(Model model) {
//         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         Cart cart = cartService.getUserCart(auth.getName());
//         model.addAttribute("cart", cart);
//         return "pages/cart";
//     }

// }
