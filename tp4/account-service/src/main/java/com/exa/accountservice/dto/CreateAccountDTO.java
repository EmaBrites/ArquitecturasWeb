package com.exa.accountservice.dto;

import com.exa.accountservice.entity.AccountTypeEnum;
import lombok.Value;

import java.io.Serializable;

@Value
public class CreateAccountDTO implements Serializable {
    Integer userId;
    AccountTypeEnum accountType;
}
