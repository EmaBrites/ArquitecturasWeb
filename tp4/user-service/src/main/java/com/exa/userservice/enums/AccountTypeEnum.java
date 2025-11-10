package com.exa.userservice.enums;

import lombok.Getter;

@Getter
public enum AccountTypeEnum {
    BASIC("BASIC"),
    PREMIUM("PREMIUM");

    private final String type;

    AccountTypeEnum(String type) {
        this.type = type;
    }
}
