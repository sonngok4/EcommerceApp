package com.example.ecommerce_app.repository;

import com.example.ecommerce_app.model.ERole;
import com.example.ecommerce_app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}