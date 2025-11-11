package com.exa.accountservice.dto;

import java.io.Serializable;

public record TransactionDTO(Integer accountId, Double amount) implements Serializable {
}
