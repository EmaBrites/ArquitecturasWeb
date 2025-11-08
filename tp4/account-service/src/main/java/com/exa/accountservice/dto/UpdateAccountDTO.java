package com.exa.accountservice.dto;

import com.exa.accountservice.entity.AccountTypeEnum;
import lombok.Value;

import java.io.Serializable;

@Value
public class UpdateAccountDTO implements Serializable {
    AccountTypeEnum accountType;
}
