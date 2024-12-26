package com.example.ecommerce_app.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.ecommerce_app.dto.UserDTO;
import com.example.ecommerce_app.model.User;
import com.example.ecommerce_app.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthWebController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        System.out.println("------>controller/auth/login");
        return "pages/auth/login";
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
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

    @GetMapping("/register")
    public String showRegistrationPage(Model model) {
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        System.out.println("------>controller/auth/register");
        return "pages/auth/register";
    }

    @GetMapping("/logout")
    public String logout() {
        System.out.println("------>controller/auth/logout");
        return "redirect:/auth/login?logout";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        System.out.println("------>controller/auth/access-denied");
        return "pages/auth/access-denied";
    }
}