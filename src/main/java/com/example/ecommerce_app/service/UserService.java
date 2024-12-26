package com.example.ecommerce_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.ecommerce_app.dto.UserDTO;
import com.example.ecommerce_app.exception.ResourceNotFoundException;
import com.example.ecommerce_app.model.ERole;
import com.example.ecommerce_app.model.Role;
import com.example.ecommerce_app.model.User;
import com.example.ecommerce_app.repository.RoleRepository;
import com.example.ecommerce_app.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerNewUser(UserDTO userDTO) {
        // Kiểm tra email đã tồn tại chưa
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Error: Email is already in use!");
        }

        // Tạo user mới
        User user = new User(
                userDTO.getFirstName(),
                userDTO.getLastName(),
                userDTO.getEmail(),
                userDTO.getUsername(),
                passwordEncoder.encode(userDTO.getPassword()));

        // Thêm ROLE_USER mặc định
        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
        user.addRole(userRole);

        return userRepository.save(user);
    }

    public void addAdminRole(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role not found."));

        user.addRole(adminRole);
        userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User updateUser(Long id, UserDTO userDTO) {
        User user = getUserById(id);

        if (userDTO.getEmail() != null && !userDTO.getEmail().isEmpty()) {
            user.setEmail(userDTO.getEmail());
        }

        if (userDTO.getUsername() != null && !userDTO.getUsername().isEmpty()) {
            user.setUsername(userDTO.getUsername());
        }

        if (userDTO.getAddress() != null && !userDTO.getAddress().isEmpty()) {
            user.setAddress(userDTO.getAddress());
        }

        if (userDTO.getPhoneNumber() != null && !userDTO.getPhoneNumber().isEmpty()) {
            user.setPhoneNumber(userDTO.getPhoneNumber());
        }

        if (userDTO.getCity() != null && !userDTO.getCity().isEmpty()) {
            user.setCity(userDTO.getCity());
        }

        if (userDTO.getCountry() != null && !userDTO.getCountry().isEmpty()) {
            user.setCountry(userDTO.getCountry());
        }

        // Chỉ cập nhật password nếu có password mới
        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public boolean isEmailUnique(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserByUsername(String name) {
        return userRepository.findByUsername(name).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
