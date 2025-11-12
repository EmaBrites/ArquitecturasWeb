package com.exa.tripservice.dto;

import lombok.Data;

@Data
public class AccountTransactionDTO {
    private Long transactionId;
    private Long accountId;
    private Double amount;
    private String type; // "CHARGE" o "TOPUP"
    private String date;
}
