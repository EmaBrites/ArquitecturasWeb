package com.exa.userservice.dto;

import com.exa.userservice.enums.AccountTypeEnum;
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
