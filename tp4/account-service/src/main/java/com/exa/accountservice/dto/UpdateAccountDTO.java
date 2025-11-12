package com.exa.accountservice.dto;

import com.exa.accountservice.enums.AccountTypeEnum;

import java.io.Serializable;

public record UpdateAccountDTO(AccountTypeEnum accountType) implements Serializable {
}
