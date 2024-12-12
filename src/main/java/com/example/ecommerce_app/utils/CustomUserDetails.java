package com.example.ecommerce_app.utils;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    private String fullName;

    public CustomUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities,
            String fullName) {
        super(username, password, authorities);
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}