package com.example.ecommerce_app.model;

// Enum để định nghĩa các role có sẵn
public enum ERole {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_MODERATOR;

    public String toUpperCase() {
        return name().toUpperCase();
    }
}