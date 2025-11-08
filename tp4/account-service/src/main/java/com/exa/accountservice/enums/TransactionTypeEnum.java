package com.exa.accountservice.enums;

import lombok.Getter;

@Getter
public enum TransactionTypeEnum {
    DEPOSIT("Deposito"),
    CHARGE("Cargo");

    private final String type;

    TransactionTypeEnum(String type) {
        this.type = type;
    }
}
