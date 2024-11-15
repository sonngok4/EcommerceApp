package com.example.ecommerce_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ecommerce_app.model.User;
import com.example.ecommerce_app.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j  // Thêm annotation này để dùng log
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Attempting to load user by email: {}", email); // Log để debug
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        log.info("User found: {}", user.getEmail()); // Log để debug

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoles().stream()
                        .map(role -> role.getName().toString().substring(5)) // Bỏ "ROLE_" prefix
                        .toArray(String[]::new))
                .build();
    }
}