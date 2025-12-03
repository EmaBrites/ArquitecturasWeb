package com.exa.gatewayservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegisterRequest {
    private CreateUserDTO createUserDTO;
    private String roles;
    private String username;
    private String password;
}
