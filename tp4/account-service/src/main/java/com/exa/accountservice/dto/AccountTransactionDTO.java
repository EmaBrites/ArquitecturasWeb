package com.exa.accountservice.dto;

import com.exa.accountservice.enums.TransactionTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AccountTransactionDTO implements Serializable {
    private Integer id;
    private Integer accountId;
    private TransactionTypeEnum transactionType;
    private Double amount;
    private LocalDateTime dateTime;
}
