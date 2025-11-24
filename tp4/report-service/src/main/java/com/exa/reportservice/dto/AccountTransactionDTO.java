package com.exa.reportservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransactionDTO implements Serializable {
    private Integer id;
    private Integer accountId;
    private String transactionType;
    private Double amount;
    private LocalDateTime dateTime;
}
