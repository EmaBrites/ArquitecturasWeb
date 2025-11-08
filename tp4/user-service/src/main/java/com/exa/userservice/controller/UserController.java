package com.exa.userservice.controller;

import com.exa.userservice.dto.*;
import com.exa.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping
    @Operation(summary = "Create a new user")
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO dto) {
        UserDTO created = userService.createUser(dto);
        URI location = URI.create(String.format("/users/%s", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing user")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UpdateUserDTO dto) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, dto));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Integer id) {
        try {
            boolean deleted = userService.deleteUser(id);
            return ResponseEntity.ok(deleted);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
