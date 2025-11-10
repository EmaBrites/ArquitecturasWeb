package com.exa.userservice.controller;

import com.exa.userservice.enums.AccountTypeEnum;
import com.exa.userservice.dto.*;
import com.exa.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create a new user")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "User created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))), @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)})
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO dto) {
        UserDTO created = userService.createUser(dto);
        URI location = URI.create(String.format("/users/%s", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    @Operation(summary = "Get all users")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of users retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))), @ApiResponse(responseCode = "204", description = "No users found", content = @Content)})
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User found successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))), @ApiResponse(responseCode = "404", description = "User not found", content = @Content)})
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update existing user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))), @ApiResponse(responseCode = "400", description = "Invalid update data", content = @Content), @ApiResponse(responseCode = "404", description = "User not found", content = @Content)})
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UpdateUserDTO dto) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, dto));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User deleted successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))), @ApiResponse(responseCode = "404", description = "User not found", content = @Content)})
    public ResponseEntity<Boolean> deleteUser(@PathVariable Integer id) {
        try {
            boolean deleted = userService.deleteUser(id);
            return ResponseEntity.ok(deleted);
        } catch (AccountNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/newAccount/{type}")
    @Operation(summary = "Associate account")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Account associated successfully", content = @Content), @ApiResponse(responseCode = "404", description = "User or account type not found", content = @Content), @ApiResponse(responseCode = "409", description = "Account already associated", content = @Content)})
    public ResponseEntity<Void> associateAccount(@PathVariable Integer id, @PathVariable AccountTypeEnum type) {
        userService.associateAccount(id, type);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/accounts/{accountId}")
    @Operation(summary = "Disassociate account")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Account disassociated successfully", content = @Content), @ApiResponse(responseCode = "404", description = "User or account not found", content = @Content), @ApiResponse(responseCode = "503", description = "Account service unavailable", content = @Content)})
    public ResponseEntity<Void> disassociateAccount(@PathVariable Integer id, @PathVariable Integer accountId) {
        try {
            userService.disassociateAccount(id, accountId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return ResponseEntity.notFound().build();
            } else if (e.getMessage().contains("unavailable")) {
                return ResponseEntity.status(503).build();
            }
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search users by email, phone or name with pagination")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Users retrieved successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))), @ApiResponse(responseCode = "204", description = "No users found", content = @Content)})
    public ResponseEntity<Page<UserDTO>> searchUsers(@RequestParam(required = false) String email, @RequestParam(required = false) String phone, @RequestParam(required = false) String name, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        Page<UserDTO> result = userService.searchUsers(email, phone, name, page, size);
        if (result.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }

}
