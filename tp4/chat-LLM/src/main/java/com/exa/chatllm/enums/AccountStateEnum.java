package com.exa.chatllm.enums;

import lombok.Getter;

@Getter
public enum AccountStateEnum {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    CLOSED("Closed");

    private final String state;

    AccountStateEnum(String state) {
        this.state = state;
    }
}
