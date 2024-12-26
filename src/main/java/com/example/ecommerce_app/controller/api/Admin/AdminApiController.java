package com.example.ecommerce_app.controller.api.Admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce_app.model.Category;

import com.example.ecommerce_app.dto.ProductRequestDTO;
import com.example.ecommerce_app.dto.ProductResponseDTO;
import com.example.ecommerce_app.dto.UserDTO;
import com.example.ecommerce_app.model.User;
import com.example.ecommerce_app.repository.UserRepository;
import com.example.ecommerce_app.service.ProductService;
import com.example.ecommerce_app.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AdminApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/products/add")
    public ResponseEntity<?> createProduct(@Valid @ModelAttribute("product") ProductRequestDTO productDTO,
            BindingResult bindingResult, Authentication authentication) {
        try {
            if (bindingResult.hasErrors()) {
                List<Map<String, String>> errors = bindingResult.getFieldErrors().stream()
                        .map(error -> Map.of(
                                "field", error.getField(),
                                "defaultMessage", error.getDefaultMessage()))
                        .collect(Collectors.toList());
                return ResponseEntity.badRequest().body(errors);
            }
            System.out.println("------>controller/admin/products/add");
            // Lấy username từ Authentication (vì CustomUserDetails chứa thông tin của user)
            String username = authentication.getName(); // This will give the email/username of the authenticated user
            // System.out.println("Username from Authentication: " + username);

            // Fetch the User entity from the UserRepository
            User currentUser = userRepository.findByEmail(username) // This will find the user based on their email
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // System.out.println("User: " + currentUser);
            ProductResponseDTO product = productService.createProduct(productDTO, currentUser);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/products/delete/{id}")
    public ResponseEntity<Void> deleteProductApi(@PathVariable Long id) {
        System.out.println("------>controller/admin/api/products/delete");
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    // Xử lý lưu sản phẩm đã sửa
    @PutMapping("/products/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id,
            @ModelAttribute("product") ProductRequestDTO productRequestDTO) throws IOException {
        System.out.println("------>controller/admin/api/products/update");
        productRequestDTO.setId(id); // Gán id từ URL vào đối tượng productRequestDTO
        ProductResponseDTO updatedProductDTO = productService.updateProduct(id, productRequestDTO);
        return ResponseEntity.ok(updatedProductDTO);
    }

    // Theo dõi khách hàng
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        System.out.println("------>controller/admin/api/users");
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    // Xóa khách hàng
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<Void> deleteUserApi(@PathVariable Long id) {
        System.out.println("------>controller/admin/api/users/delete");
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    // Chi tiết khách hàng
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        System.out.println("------>controller/admin/api/users/profile");
        User user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    // Sửa thống tin khách hàng
    @PutMapping("/users/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDTO user) {
        System.out.println("------>controller/admin/api/users/update");
        System.out.println("ID: " + id);
        System.out.println("User: " + user);
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    // Quản lý danh mục sản phẩm
    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        System.out.println("------>controller/admin/api/categories");
        List<Category> categories = productService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}
