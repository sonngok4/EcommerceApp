package com.example.ecommerce_app.controller;

import com.example.ecommerce_app.dto.UserDTO;
import com.example.ecommerce_app.provider.JwtTokenProvider;
import com.example.ecommerce_app.dto.LoginDTO;
import com.example.ecommerce_app.service.UserService;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@Slf4j
public class AuthController {

    @Autowired
    private JwtTokenProvider tokenProvider;
    
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.registerNewUser(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        log.info("Login attempt for email: {}", loginDTO.getEmail());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getEmail(),
                            loginDTO.getPassword()));
            log.info("Authentication successful for email: {}", loginDTO.getEmail());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Tạo JWT token
            String token = tokenProvider.generateToken(authentication);

            // Tạo response
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            response.put("message", "User logged in successfully!");
            response.put("email", loginDTO.getEmail());

            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            log.error("Authentication failed for email: {}", loginDTO.getEmail(), e);
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid email or password");
        }
    }
}