// package com.example.ecommerce_app.controller.api;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// import com.example.ecommerce_app.model.Cart;
// import com.example.ecommerce_app.service.CartService;

// @RestController
// @RequestMapping("/api/cart")
// public class CartApiController {
//     @Autowired
//     private CartService cartService;

//     // Automatically checks authentication
//     @GetMapping
//     public ResponseEntity<Cart> getCart() {
//         Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//         return ResponseEntity.ok(cartService.getUserCart(auth.getName()));
//     }
// }