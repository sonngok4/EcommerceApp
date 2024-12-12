package com.example.ecommerce_app.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.ecommerce_app.model.User;
import com.example.ecommerce_app.repository.UserRepository;
import com.example.ecommerce_app.utils.CustomUserDetails;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j // Thêm annotation này để dùng log
public class CustomUserDetailsService implements UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                log.info("Attempting to load user by email: {}", email); // Log để debug
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "User not found with email: " + email));

                log.info("User found: {}", user.getEmail()); // Log để debug

                System.out.println("Loading user: " + user.getEmail());
                System.out.println("User roles: " + user.getRoles());

                // Chuyển đổi role từ String[] thành List<GrantedAuthority>
                Collection<GrantedAuthority> authorities = user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
                                .collect(Collectors.toList());

                return new CustomUserDetails(user.getEmail(), user.getPassword(), authorities, user.getFullName());
        }

}