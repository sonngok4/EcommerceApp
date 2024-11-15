package com.example.ecommerce_app.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password; // Used only for registration
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
}