package com.example.ecommerce_app.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce_app.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

}
