package com.exa.userservice.dto;

import com.exa.userservice.enums.AccountTypeEnum;

import java.io.Serializable;

public record CreateAccountDTO(Integer userId, AccountTypeEnum accountType) implements Serializable {
}
