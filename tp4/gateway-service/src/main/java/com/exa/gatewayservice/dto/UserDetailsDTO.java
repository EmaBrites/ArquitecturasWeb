package com.exa.gatewayservice.dto;

import com.exa.gatewayservice.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class UserDetailsDTO implements Serializable {
    private String username;
    private String password;
    private List<RoleEnum> roles;
}
