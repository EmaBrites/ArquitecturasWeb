package com.exa.accountservice.dto;

import com.exa.accountservice.enums.AccountTypeEnum;

import java.io.Serializable;

public record CreateAccountDTO(Integer userId, AccountTypeEnum accountType) implements Serializable {
}
