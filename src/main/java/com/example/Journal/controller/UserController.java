package com.example.Journal.controller;

import com.example.Journal.entity.User;
import com.example.Journal.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.Journal.Response.ApiResponse;
import com.example.Journal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ✅ CREATE USER
    @PostMapping
    public ResponseEntity<ApiResponse<User>> createUser(
            @Valid @RequestBody User user) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User created successfully",
                        userService.saveUser(user))
        );
    }

    // ✅ GET ALL USERS
    @GetMapping
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "Users fetched successfully",
                        userService.getAllUser())
        );
    }

    // ✅ GET USER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User fetched successfully",
                        userService.getUserById(id))
        );
    }

    // ✅ UPDATE USER
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody User user) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User updated successfully",
                        userService.updateUser(id, user))
        );
    }

    // ✅ DELETE USER
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteUser(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                new ApiResponse<>(true, "User deleted successfully",
                        userService.deleteUser(id))
        );
    }
}