package com.exa.gatewayservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoleDTO {
    private String name;
    public RoleDTO(String name){
        this.name = name;
    }
}
