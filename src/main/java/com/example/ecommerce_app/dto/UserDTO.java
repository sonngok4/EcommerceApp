package com.example.ecommerce_app.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password; // Used only for registration
    private String address;
    private String phone;
}