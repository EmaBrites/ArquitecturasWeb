package com.exa.accountservice.dto;

import com.exa.accountservice.enums.AccountTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AccountDTO implements Serializable {
    private int id;
    private int userId;
    private LocalDateTime createdDate;
    private AccountTypeEnum accountType;
    private Double balance;
}
