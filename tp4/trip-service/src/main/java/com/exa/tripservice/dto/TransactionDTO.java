package com.exa.tripservice.dto;

import lombok.Data;

@Data
public class TransactionDTO {
    private Long accountId;
    private Double amount;
    private String description;
}
