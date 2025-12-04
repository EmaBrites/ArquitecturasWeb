package com.exa.gatewayservice.security;

import lombok.Getter;

public enum AuthorityConstant {
    ADMIN("ADMIN"),
    USER("USER");

    @Getter
    private final String authority;

    AuthorityConstant(String authority) {
        this.authority = authority;
    }
}