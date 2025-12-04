package com.exa.gatewayservice.dto;

import com.exa.gatewayservice.security.AuthorityConstant;
import lombok.Data;

@Data
public class RegisterRequest {
    private CreateUserDTO createUserDTO;
    private AuthorityConstant role;
    private String username;
    private String password;
}
