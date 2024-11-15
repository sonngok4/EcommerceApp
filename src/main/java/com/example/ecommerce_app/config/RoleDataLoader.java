package com.example.ecommerce_app.config;

import com.example.ecommerce_app.model.ERole;
import com.example.ecommerce_app.model.Role;
import com.example.ecommerce_app.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleDataLoader implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        // Kiểm tra và tạo các role mặc định nếu chưa tồn tại
        for (ERole role : ERole.values()) {
            if (roleRepository.findByName(role).isEmpty()) {
                Role newRole = new Role(role);
                roleRepository.save(newRole);
            }
        }
    }
}