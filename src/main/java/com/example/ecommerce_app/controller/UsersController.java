package com.example.ecommerce_app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.ecommerce_app.model.User;
import com.example.ecommerce_app.service.UserService;

@Controller
@RequestMapping("api/users") // Thêm @RequestMapping ở cấp độ class để dễ quản lý
public class UsersController {

    @Autowired
    private UserService userService;

    public UsersController() {
    }

    // 1. API get all users
    @GetMapping
    @ResponseBody
    public List<User> getListUsers() {
        System.out.println("------>controller/users/getListUsers");
        return userService.findAll();
    }

    // 2. API get user by id
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<User> getUserById(@PathVariable("id") int id) {
        for (User user : userService.findAll()) {
            if (user.getId() == id) {
                return ResponseEntity.status(200).body(user);
            }
        }
        return ResponseEntity.status(404).body(null);
    }

    // 2.1 API get user by first name
    @GetMapping("/firstName/{firstName}")
    @ResponseBody
    public ResponseEntity<User> getUserByFirstName(@PathVariable("firstName") String firstName) {
        for (User user : userService.findAll()) {
            if (user.getFirstName().equals(firstName)) {
                return ResponseEntity.status(200).body(user);
            }
        }
        return ResponseEntity.status(404).body(null);
    }
    
    // 3. API delete user by id
    @DeleteMapping("/{id}")
    @ResponseBody
    public List<User> removeUserById(@PathVariable("id") long id) {
        userService.delete(id);
        return userService.findAll();
    }

    // 4. API POST new user
    @PostMapping
    @ResponseBody
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        if (!userService.isEmailUnique(user.getEmail())) {
            return ResponseEntity.status(400).body("Email already exists");
        }
        userService.save(user);
        return ResponseEntity.status(201).body(user);
    }

    // 5. API PUT user by id
    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User updateUser) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.status(404).body(null);
        }

        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setEmail(updateUser.getEmail());
        user.setPassword(updateUser.getPassword());
        user.setPhoneNumber(updateUser.getPhoneNumber());
        user.setAddress(updateUser.getAddress());
        user.setCity(updateUser.getCity());
        user.setCountry(updateUser.getCountry());
        user.setPostalCode(updateUser.getPostalCode());

        userService.save(user);

        return ResponseEntity.status(200).body(user);

    }
}
