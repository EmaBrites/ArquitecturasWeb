package com.exa.accountservice.dto;

import com.exa.accountservice.enums.AccountTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountDTO {
    private Integer userId;
    private AccountTypeEnum accountType;
    private Double balance;
}