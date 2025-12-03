package com.exa.gatewayservice.controller;

import com.exa.gatewayservice.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.exa.gatewayservice.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signUp")
    public AuthResponse signUp(@RequestBody RegisterRequest request){
        return authService.signUp(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request){
        return authService.login(request);
    }
}
