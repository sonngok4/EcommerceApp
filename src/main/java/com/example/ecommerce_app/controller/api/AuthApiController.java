package com.example.ecommerce_app.controller.api;

import com.example.ecommerce_app.dto.UserDTO;
import com.example.ecommerce_app.model.ERole;
import com.example.ecommerce_app.model.Role;
import com.example.ecommerce_app.model.User;
import com.example.ecommerce_app.repository.UserRepository;
import com.example.ecommerce_app.dto.LoginDTO;
import com.example.ecommerce_app.service.JwtService;
import com.example.ecommerce_app.service.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@Slf4j
@RequiredArgsConstructor
public class AuthApiController {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private JwtService jwtService;

        @Autowired
        private UserService userService;

        @Autowired
        private AuthenticationManager authenticationManager;

        @PostMapping("/register")
        public ResponseEntity<?> registerUser(@Valid @ModelAttribute("user") UserDTO userDTO,
                        BindingResult bindingResult) {
                if (bindingResult.hasErrors()) {
                        List<Map<String, String>> errors = bindingResult.getFieldErrors().stream()
                                        .map(error -> Map.of(
                                                        "field", error.getField(),
                                                        "defaultMessage", error.getDefaultMessage()))
                                        .collect(Collectors.toList());
                        return ResponseEntity.badRequest().body(errors);
                }

                userService.registerNewUser(userDTO);
                return ResponseEntity.ok().body(Map.of("redirectUrl", "/auth/login"));
        }

        @PostMapping("/login")
        public ResponseEntity<?> login(
                        @RequestBody LoginDTO loginDTO,
                        HttpServletResponse response) {
                try {
                        // Authenticate user
                        Authentication authentication = authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        loginDTO.getEmail(),
                                                        loginDTO.getPassword()));

                        // System.out.println("Authentication: " + authentication);

                        // Get user details
                        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

                        // Generate JWT token
                        String jwtToken = jwtService.generateToken(userDetails);

                        // Create JWT cookie
                        Cookie jwtCookie = new Cookie("jwt", jwtToken);
                        jwtCookie.setHttpOnly(true);
                        jwtCookie.setPath("/");
                        response.addCookie(jwtCookie);

                        // Find user and get role
                        User user = userRepository.findByEmail(userDetails.getUsername())
                                        .orElseThrow(() -> new RuntimeException("User not found"));

                        // Return response with token and role
                        return ResponseEntity.ok(new AuthResponse(jwtToken, user.getRoles().stream().map(Role::getName).collect(Collectors.toList())));
                } catch (BadCredentialsException e) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body(new ErrorResponse("Invalid username or password"));
                }
        }

        // Error response DTO
        public record ErrorResponse(String message) {
        }

        // Existing AuthResponse record
        public record AuthResponse(String token, List<ERole> roles) {
        }
}