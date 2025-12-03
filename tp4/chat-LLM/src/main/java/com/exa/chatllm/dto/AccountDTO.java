package com.exa.chatllm.dto;

import com.exa.chatllm.enums.AccountStateEnum;
import com.exa.chatllm.enums.AccountTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AccountDTO implements Serializable {
    private int id;
    private int userId;
    private LocalDateTime createdDate;
    private AccountTypeEnum accountType;
    private AccountStateEnum accountState;
    private Double balance;
}
