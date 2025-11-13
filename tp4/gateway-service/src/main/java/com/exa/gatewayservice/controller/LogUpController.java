package com.exa.gatewayservice.controller;

import com.exa.gatewayservice.dto.CreateUserDTO;
import com.exa.gatewayservice.dto.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.exa.gatewayservice.service.LogUpService;

@RestController
@RequestMapping("/api")
public class LogUpController {
    private final LogUpService logUpService;

    public LogUpController(LogUpService logUpService) {
        this.logUpService = logUpService;
    }

    @PostMapping("/logup")
    public ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO createUserDTO) {
        return ResponseEntity.ok(logUpService.createUser(createUserDTO));
    }
}
